package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.payment.PaymentRequestDto;
import com.example.be.web.doman.dto.response.payment.InvoiceResponseDto;
import com.example.be.web.doman.dto.response.payment.PaymentResponseDto;
import com.example.be.web.doman.entity.*;
import com.example.be.web.doman.mapper.InvoiceMapper;
import com.example.be.web.doman.mapper.PaymentMapper;
import com.example.be.web.doman.model.InvoiceStatus;
import com.example.be.web.doman.model.PaymentStatus;
import com.example.be.web.exception.extended.BadRequestException;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.ClassRegistrationRepository;
import com.example.be.web.repository.InvoiceRepository;
import com.example.be.web.repository.PaymentRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.security.UserPrincipal;
import com.example.be.web.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Triển khai {@link PaymentService}.
 *
 * @author auto-generated
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final ClassRegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final PaymentMapper paymentMapper;
    private final InvoiceMapper invoiceMapper;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto) {
        User currentUser = getCurrentUser();

        if (paymentRepository.existsByClassRegistration_RegistrationId(requestDto.getRegistrationId())) {
            throw new BadRequestException(ErrorMessage.Payment.PAYMENT_ALREADY_EXISTS);
        }

        ClassRegistration registration = registrationRepository.findById(requestDto.getRegistrationId())
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.ClassRegistration.REGISTRATION_NOT_FOUND,
                        new String[]{requestDto.getRegistrationId().toString()}));

        Payment payment = Payment.builder()
                .user(currentUser)
                .classRegistration(registration)
                .amount(requestDto.getAmount())
                .paymentMethod(requestDto.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .transactionCode(requestDto.getTransactionCode())
                .proofImageUrl(requestDto.getProofImageUrl())
                .paidAt(LocalDateTime.now())
                .note(requestDto.getNote())
                .build();

        Payment saved = paymentRepository.save(payment);
        log.info("Đã tạo thanh toán ID: {}", saved.getPaymentId());
        return paymentMapper.toResponse(saved);
    }

    @Override
    public PaymentResponseDto getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Payment.PAYMENT_NOT_FOUND,
                        new String[]{paymentId.toString()}));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public Page<PaymentResponseDto> getMyPayments(Pageable pageable) {
        User currentUser = getCurrentUser();
        return paymentRepository.findByUser_Id(currentUser.getId(), pageable)
                .map(paymentMapper::toResponse);
    }

    @Override
    public Page<PaymentResponseDto> getPaymentsByStatus(PaymentStatus status, Pageable pageable) {
        return paymentRepository.findByPaymentStatus(status, pageable)
                .map(paymentMapper::toResponse);
    }

    /**
     * Xác nhận thanh toán và tự động tạo hóa đơn.
     */
    @Override
    public PaymentResponseDto confirmPayment(Long paymentId) {
        User currentUser = getCurrentUser();
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Payment.PAYMENT_NOT_FOUND,
                        new String[]{paymentId.toString()}));

        if (payment.getPaymentStatus() != PaymentStatus.PENDING
                && payment.getPaymentStatus() != PaymentStatus.PAID) {
            throw new BadRequestException(ErrorMessage.Payment.INVALID_STATUS_TRANSITION);
        }

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        payment.setConfirmedBy(currentUser);
        payment.setConfirmedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        // Tự động tạo hóa đơn
        if (!invoiceRepository.existsByPayment_PaymentId(paymentId)) {
            Invoice invoice = Invoice.builder()
                    .payment(payment)
                    .invoiceCode("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                    .user(payment.getUser())
                    .amount(payment.getAmount())
                    .issueDate(LocalDate.now())
                    .status(InvoiceStatus.ISSUED)
                    .build();
            invoiceRepository.save(invoice);
            log.info("Đã tạo hóa đơn cho payment ID: {}", paymentId);
        }

        log.info("Đã xác nhận thanh toán ID: {}", paymentId);
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponseDto rejectPayment(Long paymentId, String note) {
        User currentUser = getCurrentUser();
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Payment.PAYMENT_NOT_FOUND,
                        new String[]{paymentId.toString()}));

        payment.setPaymentStatus(PaymentStatus.REJECTED);
        payment.setConfirmedBy(currentUser);
        payment.setConfirmedAt(LocalDateTime.now());
        payment.setNote(note);
        paymentRepository.save(payment);

        log.info("Đã từ chối thanh toán ID: {}", paymentId);
        return paymentMapper.toResponse(payment);
    }

    @Override
    public InvoiceResponseDto getInvoiceByPayment(Long paymentId) {
        Invoice invoice = invoiceRepository.findByPayment_PaymentId(paymentId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Payment.INVOICE_NOT_FOUND,
                        new String[]{paymentId.toString()}));
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponseDto getInvoiceByCode(String invoiceCode) {
        Invoice invoice = invoiceRepository.findByInvoiceCode(invoiceCode)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Payment.INVOICE_NOT_FOUND,
                        new String[]{invoiceCode}));
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public Page<InvoiceResponseDto> getMyInvoices(Pageable pageable) {
        User currentUser = getCurrentUser();
        return invoiceRepository.findByUser_Id(currentUser.getId(), pageable)
                .map(invoiceMapper::toResponse);
    }

    private User getCurrentUser() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userRepository.findById(principal.getId())
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.User.USER_NOT_FOUND_ID,
                        new String[]{principal.getId().toString()}));
    }
}
