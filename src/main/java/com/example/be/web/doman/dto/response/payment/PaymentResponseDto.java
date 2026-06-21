package com.example.be.web.doman.dto.response.payment;

import com.example.be.web.doman.model.PaymentMethod;
import com.example.be.web.doman.model.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO trả về thông tin thanh toán.
 *
 * @author auto-generated
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin thanh toán")
public class PaymentResponseDto {

    @Schema(description = "ID thanh toán")
    private Long paymentId;
    @Schema(description = "ID sinh viên")
    private Long userId;
    @Schema(description = "Tên sinh viên")
    private String userName;
    @Schema(description = "ID đăng ký lớp")
    private Long registrationId;
    @Schema(description = "Số tiền")
    private BigDecimal amount;
    @Schema(description = "Phương thức")
    private PaymentMethod paymentMethod;
    @Schema(description = "Trạng thái")
    private PaymentStatus paymentStatus;
    @Schema(description = "Mã giao dịch")
    private String transactionCode;
    @Schema(description = "URL ảnh minh chứng")
    private String proofImageUrl;
    @Schema(description = "Thời điểm thanh toán")
    private LocalDateTime paidAt;
    @Schema(description = "ID người xác nhận")
    private Long confirmedById;
    @Schema(description = "Tên người xác nhận")
    private String confirmedByName;
    @Schema(description = "Thời điểm xác nhận")
    private LocalDateTime confirmedAt;
    @Schema(description = "Ghi chú")
    private String note;
    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;
}
