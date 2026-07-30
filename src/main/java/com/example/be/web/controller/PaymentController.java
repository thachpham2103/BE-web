package com.example.be.web.controller;

import com.example.be.web.base.RestData;
import com.example.be.web.base.VsResponseUtil;
import com.example.be.web.doman.dto.request.payment.PaymentRequestDto;
import com.example.be.web.doman.model.PaymentStatus;
import com.example.be.web.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller quản lý thanh toán và hóa đơn.
 *
 * @author auto-generated
 */
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "API thanh toán và hóa đơn")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Tạo thanh toán", description = "Sinh viên tạo thanh toán")
    public ResponseEntity<RestData<?>> createPayment(@Valid @RequestBody PaymentRequestDto dto) {
        return VsResponseUtil.success(HttpStatus.CREATED, paymentService.createPayment(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy thanh toán theo ID")
    public ResponseEntity<RestData<?>> getPaymentById(@PathVariable Long id) {
        return VsResponseUtil.success(paymentService.getPaymentById(id));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy thanh toán của tôi")
    public ResponseEntity<RestData<?>> getMyPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return VsResponseUtil.success(paymentService.getMyPayments(pageable));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Lấy thanh toán theo trạng thái", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> getByStatus(
            @PathVariable PaymentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return VsResponseUtil.success(paymentService.getPaymentsByStatus(status, pageable));
    }

    @PutMapping("/{id}/confirm")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Xác nhận thanh toán (tự động tạo hóa đơn)", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> confirmPayment(@PathVariable Long id) {
        return VsResponseUtil.success(paymentService.confirmPayment(id));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Từ chối thanh toán", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> rejectPayment(
            @PathVariable Long id, @RequestParam(required = false) String note) {
        return VsResponseUtil.success(paymentService.rejectPayment(id, note));
    }

    @PutMapping("/{id}/pay")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Học sinh thực hiện thanh toán", description = "Học sinh tự thanh toán")
    public ResponseEntity<RestData<?>> payPayment(@PathVariable Long id) {
        return VsResponseUtil.success(paymentService.payPayment(id));
    }

    // ======================== INVOICE ========================

    @PostMapping("/invoice")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Tạo hóa đơn thủ công", description = "Giáo viên/Admin tạo hóa đơn thủ công cho học viên")
    public ResponseEntity<RestData<?>> createManualInvoice(@Valid @RequestBody com.example.be.web.doman.dto.request.payment.ManualInvoiceRequestDto dto) {
        return VsResponseUtil.success(HttpStatus.CREATED, paymentService.createManualInvoice(dto));
    }

    @GetMapping("/{paymentId}/invoice")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy hóa đơn theo payment ID")
    public ResponseEntity<RestData<?>> getInvoiceByPayment(@PathVariable Long paymentId) {
        return VsResponseUtil.success(paymentService.getInvoiceByPayment(paymentId));
    }

    @GetMapping("/invoices/code/{code}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy hóa đơn theo mã")
    public ResponseEntity<RestData<?>> getInvoiceByCode(@PathVariable String code) {
        return VsResponseUtil.success(paymentService.getInvoiceByCode(code));
    }

    @GetMapping("/invoices/me")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy hóa đơn của tôi")
    public ResponseEntity<RestData<?>> getMyInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return VsResponseUtil.success(paymentService.getMyInvoices(pageable));
    }
}
