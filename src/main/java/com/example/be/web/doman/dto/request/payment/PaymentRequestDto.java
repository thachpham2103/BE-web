package com.example.be.web.doman.dto.request.payment;

import com.example.be.web.doman.model.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO nhận dữ liệu tạo thanh toán.
 *
 * @author auto-generated
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request body tạo thanh toán")
public class PaymentRequestDto {

    @NotNull(message = "Mã đăng ký không được để trống")
    @Schema(description = "ID đăng ký lớp", example = "1")
    private Long registrationId;

    @NotNull(message = "Số tiền không được để trống")
    @DecimalMin(value = "0.01", message = "Số tiền phải > 0")
    @Schema(description = "Số tiền thanh toán", example = "1500000")
    private BigDecimal amount;

    @NotNull(message = "Phương thức thanh toán không được để trống")
    @Schema(description = "Phương thức thanh toán", example = "BANK_TRANSFER")
    private PaymentMethod paymentMethod;

    @Schema(description = "Mã giao dịch", example = "TXN123456")
    private String transactionCode;

    @Schema(description = "URL ảnh minh chứng")
    private String proofImageUrl;

    @Schema(description = "Ghi chú")
    private String note;
}
