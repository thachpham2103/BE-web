package com.example.be.web.repository;

import com.example.be.web.doman.entity.Notification;
import com.example.be.web.doman.model.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByUser_Id(Long userId, Pageable pageable);

    Page<Notification> findByUser_IdAndIsReadFalse(Long userId, Pageable pageable);

    Page<Notification> findByType(NotificationType type, Pageable pageable);

    long countByUser_IdAndIsReadFalse(Long userId);
}
