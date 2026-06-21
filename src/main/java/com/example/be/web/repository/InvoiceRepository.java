package com.example.be.web.repository;

import com.example.be.web.doman.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository thao tác dữ liệu {@link Invoice}.
 *
 * @author auto-generated
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByPayment_PaymentId(Long paymentId);

    Optional<Invoice> findByInvoiceCode(String invoiceCode);

    boolean existsByPayment_PaymentId(Long paymentId);

    Page<Invoice> findByUser_Id(Long userId, Pageable pageable);
}
