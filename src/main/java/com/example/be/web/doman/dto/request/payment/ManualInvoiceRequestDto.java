package com.example.be.web.doman.dto.request.payment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManualInvoiceRequestDto {
    @NotNull(message = "Số tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Số tiền phải lớn hơn 0")
    private BigDecimal amount;

    @NotBlank(message = "Lý do (nội dung hóa đơn) không được để trống")
    private String reason;

    @NotBlank(message = "studentId không được để trống")
    private String studentId;

    @NotNull(message = "classId không được để trống")
    private Long classId;
}
