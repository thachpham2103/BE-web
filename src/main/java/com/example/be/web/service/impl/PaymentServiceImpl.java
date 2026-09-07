package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.payment.ManualInvoiceRequestDto;
import com.example.be.web.doman.dto.request.payment.PaymentRequestDto;
import com.example.be.web.doman.dto.response.payment.InvoiceResponseDto;
import com.example.be.web.doman.dto.response.payment.PaymentResponseDto;
import com.example.be.web.doman.entity.Invoice;
import com.example.be.web.doman.entity.Payment;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.mapper.InvoiceMapper;
import com.example.be.web.doman.mapper.PaymentMapper;
import com.example.be.web.doman.model.ActivityAction;
import com.example.be.web.doman.model.InvoiceStatus;
import com.example.be.web.doman.model.PaymentRegistrationStatus;
import com.example.be.web.doman.model.PaymentStatus;
import com.example.be.web.doman.model.TargetType;
import com.example.be.web.exception.extended.BadRequestException;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.InvoiceRepository;
import com.example.be.web.repository.PaymentRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.security.UserPrincipal;
import com.example.be.web.service.ActivityLogService;
import com.example.be.web.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final PaymentMapper paymentMapper;
    private final InvoiceMapper invoiceMapper;
    private final ActivityLogService activityLogService;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto dto) {
        throw new BadRequestException(
                ErrorMessage.Payment.MANUAL_PAYMENT_NOT_ALLOWED
        );
    }

    @Override
    public InvoiceResponseDto createManualInvoice(
            ManualInvoiceRequestDto dto
    ) {
        throw new BadRequestException(
                ErrorMessage.Payment.MANUAL_INVOICE_NOT_ALLOWED
        );
    }

    @Override
    public PaymentResponseDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> paymentNotFound(id));

        requireOwnerOrReviewer(payment);
        return paymentMapper.toResponse(payment);
    }

    @Override
    public Page<PaymentResponseDto> getMyPayments(Pageable pageable) {
        return paymentRepository
                .findByUser_Id(getCurrentUser().getId(), pageable)
                .map(paymentMapper::toResponse);
    }

    @Override
    public Page<PaymentResponseDto> getPaymentsByStatus(
            PaymentStatus status,
            Pageable pageable
    ) {

        requireReviewer();

        return paymentRepository
                .findByPaymentStatus(status, pageable)
                .map(paymentMapper::toResponse);
    }

    @Override
    public PaymentResponseDto payPayment(
            Long id,
            String proofImageUrl
    ) {

        User student = getCurrentUser();
        Payment payment = lockedPayment(id);

        if (!payment.getUser().getId().equals(student.getId())) {
            throw new BadRequestException(
                    ErrorMessage.Payment.NO_PERMISSION_PAY_PAYMENT
            );
        }

        if (payment.getPaymentStatus() != PaymentStatus.PENDING
                && payment.getPaymentStatus() != PaymentStatus.REJECTED) {

            throw new BadRequestException(
                    ErrorMessage.Payment.INVALID_PAYMENT_STATUS
            );
        }

        if (proofImageUrl == null
                || proofImageUrl.isBlank()
                || proofImageUrl.trim().length() > 500) {

            throw new BadRequestException(
                    ErrorMessage.Payment.INVALID_PAYMENT_PROOF
            );
        }

        // PAID ở đây nghĩa là sinh viên đã gửi minh chứng, chờ admin duyệt.
        payment.setProofImageUrl(proofImageUrl.trim());
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());
        payment.setConfirmedBy(null);
        payment.setConfirmedAt(null);
        payment.setNote(null);

        // Chưa được coi là hoàn tất cho đến khi admin xác nhận.
        payment.getClassRegistration().setPaymentStatus(
                PaymentRegistrationStatus.UNPAID
        );

        paymentRepository.save(payment);
        saveInvoice(payment, InvoiceStatus.ISSUED);

        logPayment(
                student,
                payment,
                "Gửi minh chứng thanh toán học phí"
        );

        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponseDto confirmPayment(Long id) {

        requireReviewer();

        User reviewer = getCurrentUser();
        Payment payment = lockedPayment(id);

        requireAwaitingReview(payment);

        if (payment.getProofImageUrl() == null
                || payment.getProofImageUrl().isBlank()) {

            throw new BadRequestException(
                    ErrorMessage.Payment.PAYMENT_PROOF_NOT_FOUND
            );
        }

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        payment.setConfirmedBy(reviewer);
        payment.setConfirmedAt(LocalDateTime.now());

        payment.getClassRegistration().setPaymentStatus(
                PaymentRegistrationStatus.PAID
        );

        paymentRepository.save(payment);
        saveInvoice(payment, InvoiceStatus.PAID);

        logPayment(
                reviewer,
                payment,
                "Xác nhận thanh toán học phí"
        );

        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponseDto rejectPayment(Long id, String note) {

        requireReviewer();

        User reviewer = getCurrentUser();
        Payment payment = lockedPayment(id);

        requireAwaitingReview(payment);

        if (note == null || note.isBlank()) {
            throw new BadRequestException(
                    ErrorMessage.Payment.REJECT_REASON_REQUIRED
            );
        }

        payment.setPaymentStatus(PaymentStatus.REJECTED);
        payment.setConfirmedBy(reviewer);
        payment.setConfirmedAt(LocalDateTime.now());
        payment.setNote(note.trim());

        payment.getClassRegistration().setPaymentStatus(
                PaymentRegistrationStatus.UNPAID
        );

        paymentRepository.save(payment);
        saveInvoice(payment, InvoiceStatus.ISSUED);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public InvoiceResponseDto getInvoiceByPayment(Long paymentId) {

        Invoice invoice = invoiceRepository
                .findByPayment_PaymentId(paymentId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Payment.INVOICE_NOT_FOUND,
                        new String[]{paymentId.toString()}
                ));

        requireOwnerOrReviewer(invoice.getPayment());

        return invoiceResponse(invoice);
    }

    @Override
    public InvoiceResponseDto getInvoiceByCode(String code) {

        Invoice invoice = invoiceRepository
                .findByInvoiceCode(code)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Payment.INVOICE_NOT_FOUND,
                        new String[]{code}
                ));

        requireOwnerOrReviewer(invoice.getPayment());

        return invoiceResponse(invoice);
    }

    @Override
    public Page<InvoiceResponseDto> getMyInvoices(Pageable pageable) {

        return invoiceRepository
                .findByUser_Id(getCurrentUser().getId(), pageable)
                .map(this::invoiceResponse);
    }

    private InvoiceResponseDto invoiceResponse(Invoice invoice) {

        InvoiceResponseDto dto = invoiceMapper.toResponse(invoice);

        // Dữ liệu cũ có thể ghi PAID ngay khi sinh viên gửi ảnh.
        // Chỉ Payment CONFIRMED mới được hiển thị là hoàn tất.
        PaymentStatus status = invoice.getPayment().getPaymentStatus();

        if (status == PaymentStatus.CONFIRMED) {
            dto.setStatus(InvoiceStatus.PAID);

        } else if (invoice.getStatus() == InvoiceStatus.PAID) {
            dto.setStatus(InvoiceStatus.ISSUED);
        }

        return dto;
    }

    private void saveInvoice(
            Payment payment,
            InvoiceStatus status
    ) {

        Invoice invoice = invoiceRepository
                .findByPayment_PaymentId(payment.getPaymentId())
                .orElseGet(() -> Invoice.builder()
                        .payment(payment)
                        .invoiceCode("INV-" + UUID.randomUUID())
                        .user(payment.getUser())
                        .amount(payment.getAmount())
                        .issueDate(LocalDate.now())
                        .build());

        invoice.setStatus(status);
        invoiceRepository.save(invoice);
    }

    private Payment lockedPayment(Long id) {

        return paymentRepository.findForUpdate(id)
                .orElseThrow(() -> paymentNotFound(id));
    }

    private NotFoundException paymentNotFound(Long id) {

        return new NotFoundException(
                ErrorMessage.Payment.PAYMENT_NOT_FOUND,
                new String[]{id.toString()}
        );
    }

    private void requireAwaitingReview(Payment payment) {

        if (payment.getPaymentStatus() != PaymentStatus.PAID) {

            throw new BadRequestException(
                    ErrorMessage.Payment.PAYMENT_NOT_AWAITING_REVIEW
            );
        }
    }

    private boolean isReviewer() {

        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN")
                                || a.getAuthority().equals("ROLE_LEADER")
                );
    }

    private void requireReviewer() {

        if (!isReviewer()) {

            throw new BadRequestException(
                    ErrorMessage.Payment.NO_PERMISSION_REVIEW_PAYMENT
            );
        }
    }

    private void requireOwnerOrReviewer(Payment payment) {

        if (!isReviewer()
                && !payment.getUser().getId()
                .equals(getCurrentUser().getId())) {

            throw new BadRequestException(
                    ErrorMessage.Payment.NO_PERMISSION_VIEW_PAYMENT
            );
        }
    }

    private User getCurrentUser() {

        UserPrincipal principal = (UserPrincipal)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        return userRepository.findById(principal.getId())
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.User.USER_NOT_FOUND_ID,
                        new String[]{principal.getId().toString()}
                ));
    }

    private void logPayment(
            User user,
            Payment payment,
            String message
    ) {

        try {

            activityLogService.logActivity(
                    user.getUsername(),
                    ActivityAction.UPDATE,
                    TargetType.PAYMENT,
                    payment.getPaymentId(),
                    message,
                    null
            );

        } catch (Exception ignored) {
        }
    }
}
