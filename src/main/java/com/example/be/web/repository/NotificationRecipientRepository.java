package com.example.be.web.repository;

import com.example.be.web.doman.entity.NotificationRecipient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Long> {

    Page<NotificationRecipient> findByUser_Id(Long userId, Pageable pageable);

    Page<NotificationRecipient> findByUser_IdAndIsReadFalse(Long userId, Pageable pageable);

    Optional<NotificationRecipient> findByNotification_NotifIdAndUser_Id(Long notifId, Long userId);

    long countByUser_IdAndIsReadFalse(Long userId);
}
