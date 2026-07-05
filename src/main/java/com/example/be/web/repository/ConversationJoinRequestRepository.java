package com.example.be.web.repository;

import com.example.be.web.doman.entity.ConversationJoinRequest;
import com.example.be.web.doman.model.JoinRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationJoinRequestRepository extends JpaRepository<ConversationJoinRequest, Long> {

    List<ConversationJoinRequest> findByStatus(JoinRequestStatus status);

    List<ConversationJoinRequest> findByConversation_ConvoIdAndStatus(Long convoId, JoinRequestStatus status);
    
    Optional<ConversationJoinRequest> findByConversation_ConvoIdAndUser_IdAndStatus(Long convoId, Long userId, JoinRequestStatus status);
}
