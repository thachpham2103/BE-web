package com.example.be.web.doman.entity;

import com.example.be.web.doman.model.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity đại diện cho hóa đơn ({@code Invoice}).
 *
 * <p>Liên kết 1-1 với {@link Payment}. Mỗi hóa đơn có mã duy nhất {@code invoiceCode}.</p>
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "invoices",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_INVOICE_PAYMENT", columnNames = {"payment_id"}),
                @UniqueConstraint(name = "UK_INVOICE_CODE", columnNames = {"invoice_code"})
        })
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false, updatable = false)
    private Long invoiceId;

    /** Thanh toán liên quan (1-1). */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "FK_INVOICE_PAYMENT"))
    private Payment payment;

    /** Mã hóa đơn duy nhất. */
    @Column(name = "invoice_code", length = 50, nullable = false, unique = true)
    private String invoiceCode;

    /** Người nhận hóa đơn. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_INVOICE_USER"))
    private User user;

    /** Số tiền trên hóa đơn. */
    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    /** Ngày phát hành. */
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    /** Đường dẫn file hóa đơn (PDF). */
    @Column(name = "file_url", length = 500)
    private String fileUrl;

    /** Trạng thái hóa đơn. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private InvoiceStatus status = InvoiceStatus.DRAFT;

    /** Ngày tạo. */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
