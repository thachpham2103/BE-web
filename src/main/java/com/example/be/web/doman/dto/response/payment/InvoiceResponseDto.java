package com.example.be.web.doman.dto.response.payment;

import com.example.be.web.doman.model.InvoiceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO trả về thông tin hóa đơn.
 *
 * @author auto-generated
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin hóa đơn")
public class InvoiceResponseDto {

    @Schema(description = "ID hóa đơn")
    private Long invoiceId;
    @Schema(description = "ID thanh toán")
    private Long paymentId;
    @Schema(description = "Mã hóa đơn")
    private String invoiceCode;
    @Schema(description = "ID người nhận")
    private Long userId;
    @Schema(description = "Tên người nhận")
    private String userName;
    @Schema(description = "Số tiền")
    private BigDecimal amount;
    @Schema(description = "Ngày phát hành")
    private LocalDate issueDate;
    @Schema(description = "URL file hóa đơn")
    private String fileUrl;
    @Schema(description = "Trạng thái")
    private InvoiceStatus status;
    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;
}
