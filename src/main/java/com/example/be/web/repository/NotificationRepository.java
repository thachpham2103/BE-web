package com.example.be.web.repository;

import com.example.be.web.doman.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificationRepository extends JpaRepository<Notification, Long>,
        JpaSpecificationExecutor<Notification> {

    Page<Notification> findByUser_Id(Long userId, Pageable pageable);

    long countByUser_IdAndIsReadFalse(Long userId);
}