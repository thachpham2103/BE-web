package com.example.be.web.repository;

import com.example.be.web.doman.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepository extends JpaRepository<Notification, Long>,
        JpaSpecificationExecutor<Notification> {

    Page<Notification> findByUser_Id(Long userId, Pageable pageable);

    long countByUser_IdAndIsReadFalse(Long userId);

    @Query("SELECT n FROM Notification n WHERE n.notifId IN " +
           "(SELECT MIN(n2.notifId) FROM Notification n2 WHERE n2.createdBy.username = :username " +
           "GROUP BY n2.title, n2.body, n2.type, n2.createAt) " +
           "ORDER BY n.createAt DESC")
    Page<Notification> findDistinctSentNotifications(@Param("username") String username, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.title = :newTitle, n.body = :newBody WHERE n.title = :oldTitle AND n.body = :oldBody AND n.createdBy.username = :username")
    int updateSentNotifications(@Param("oldTitle") String oldTitle, @Param("oldBody") String oldBody, 
                                @Param("newTitle") String newTitle, @Param("newBody") String newBody, 
                                @Param("username") String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM Notification n WHERE n.title = :title AND n.body = :body AND n.createdBy.username = :username")
    int deleteSentNotifications(@Param("title") String title, @Param("body") String body, @Param("username") String username);
}