package com.example.be.web.repository;

import com.example.be.web.doman.entity.MessageAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageAttachmentRepository extends JpaRepository<MessageAttachment, Long> {

    List<MessageAttachment> findByMessage_MessageId(Long messageId);
}
