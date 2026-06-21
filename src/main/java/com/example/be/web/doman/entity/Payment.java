package com.example.be.web.doman.entity;

import com.example.be.web.doman.entity.common.DateAuditing;
import com.example.be.web.doman.model.PaymentMethod;
import com.example.be.web.doman.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity đại diện cho thanh toán ({@code Payment}).
 *
 * <p>Liên kết 1-1 với {@link ClassRegistration} (mỗi đăng ký chỉ có 1 thanh toán).
 * Có thể sinh ra {@link Invoice} sau khi được xác nhận.</p>
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_PAYMENT_REGISTRATION",
                columnNames = {"registration_id"}
        ))
public class Payment extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false, updatable = false)
    private Long paymentId;

    /** Sinh viên thanh toán. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PAYMENT_USER"))
    private User user;

    /** Đăng ký lớp học liên quan. */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "FK_PAYMENT_REGISTRATION"))
    private ClassRegistration classRegistration;

    /** Số tiền thanh toán. */
    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    /** Phương thức thanh toán. */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 20, nullable = false)
    private PaymentMethod paymentMethod;

    /** Trạng thái thanh toán. */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 20, nullable = false)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    /** Mã giao dịch. */
    @Column(name = "transaction_code", length = 100)
    private String transactionCode;

    /** Ảnh minh chứng thanh toán. */
    @Column(name = "proof_image_url", length = 500)
    private String proofImageUrl;

    /** Thời điểm thanh toán. */
    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    /** Người xác nhận thanh toán. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confirmed_by",
            foreignKey = @ForeignKey(name = "FK_PAYMENT_CONFIRMER"))
    private User confirmedBy;

    /** Thời điểm xác nhận. */
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    /** Ghi chú. */
    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    /** Hóa đơn liên kết. */
    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Invoice invoice;
}
