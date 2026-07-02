package com.example.be.web.repository;

import com.example.be.web.doman.entity.PinnedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PinnedMessageRepository extends JpaRepository<PinnedMessage, Long> {

    List<PinnedMessage> findByConversation_ConvoId(Long convoId);

    Optional<PinnedMessage> findByConversation_ConvoIdAndMessage_MessageId(Long convoId, Long messageId);

    boolean existsByConversation_ConvoIdAndMessage_MessageId(Long convoId, Long messageId);
}
