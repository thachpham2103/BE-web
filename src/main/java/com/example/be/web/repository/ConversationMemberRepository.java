package com.example.be.web.repository;

import com.example.be.web.doman.entity.ConversationMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationMemberRepository
        extends JpaRepository<ConversationMember, ConversationMember.ConversationMemberId> {

    List<ConversationMember> findByConversation_ConvoIdAndLeftAtIsNull(Long convoId);

    Optional<ConversationMember> findByConversation_ConvoIdAndUser_Id(Long convoId, Long userId);

    boolean existsByConversation_ConvoIdAndUser_IdAndLeftAtIsNull(Long convoId, Long userId);
}
