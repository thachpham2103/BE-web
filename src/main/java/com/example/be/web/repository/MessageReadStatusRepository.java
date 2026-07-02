package com.example.be.web.repository;

import com.example.be.web.doman.entity.MessageReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageReadStatusRepository extends JpaRepository<MessageReadStatus, Long> {

    List<MessageReadStatus> findByMessage_MessageId(Long messageId);

    Optional<MessageReadStatus> findByMessage_MessageIdAndUser_Id(Long messageId, Long userId);

    boolean existsByMessage_MessageIdAndUser_Id(Long messageId, Long userId);
}
