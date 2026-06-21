package com.example.be.web.repository;

import com.example.be.web.doman.entity.Payment;
import com.example.be.web.doman.model.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository thao tác dữ liệu {@link Payment}.
 *
 * @author auto-generated
 */
@Repository
public interface PaymentRepository
        extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

    Optional<Payment> findByClassRegistration_RegistrationId(Long registrationId);

    boolean existsByClassRegistration_RegistrationId(Long registrationId);

    Page<Payment> findByUser_Id(Long userId, Pageable pageable);

    Page<Payment> findByPaymentStatus(PaymentStatus status, Pageable pageable);

    long countByPaymentStatus(PaymentStatus status);
}
