package com.example.be.web.service;

import com.example.be.web.doman.dto.request.payment.PaymentRequestDto;
import com.example.be.web.doman.dto.response.payment.InvoiceResponseDto;
import com.example.be.web.doman.dto.response.payment.PaymentResponseDto;
import com.example.be.web.doman.model.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface quản lý thanh toán và hóa đơn.
 *
 * @author auto-generated
 */
public interface PaymentService {

    /** Tạo thanh toán. */
    PaymentResponseDto createPayment(PaymentRequestDto requestDto);

    /** Lấy thanh toán theo ID. */
    PaymentResponseDto getPaymentById(Long paymentId);

    /** Lấy thanh toán của user hiện tại. */
    Page<PaymentResponseDto> getMyPayments(Pageable pageable);

    /** Lấy thanh toán theo trạng thái. */
    Page<PaymentResponseDto> getPaymentsByStatus(PaymentStatus status, Pageable pageable);

    /** Admin/Leader xác nhận thanh toán. */
    PaymentResponseDto confirmPayment(Long paymentId);

    /** Admin/Leader từ chối thanh toán. */
    PaymentResponseDto rejectPayment(Long paymentId, String note);

    /** Lấy hóa đơn theo payment. */
    InvoiceResponseDto getInvoiceByPayment(Long paymentId);

    /** Lấy hóa đơn theo mã. */
    InvoiceResponseDto getInvoiceByCode(String invoiceCode);

    /** Lấy hóa đơn của user hiện tại. */
    Page<InvoiceResponseDto> getMyInvoices(Pageable pageable);

    /** Tạo hóa đơn thủ công. */
    InvoiceResponseDto createManualInvoice(com.example.be.web.doman.dto.request.payment.ManualInvoiceRequestDto requestDto);
}
