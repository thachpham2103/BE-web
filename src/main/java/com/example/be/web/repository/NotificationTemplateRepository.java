package com.example.be.web.repository;

import com.example.be.web.doman.entity.NotificationTemplate;
import com.example.be.web.doman.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {

    Optional<NotificationTemplate> findByName(String name);

    List<NotificationTemplate> findByType(NotificationType type);

    List<NotificationTemplate> findByActiveTrue();
}
