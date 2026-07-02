package com.example.be.web.repository;

import com.example.be.web.doman.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByConversation_ConvoIdAndDeletedFalse(Long convoId, Pageable pageable);

    long countByConversation_ConvoIdAndDeletedFalse(Long convoId);
}
