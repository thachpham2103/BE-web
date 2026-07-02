package com.example.be.web.repository;

import com.example.be.web.doman.entity.Conversation;
import com.example.be.web.doman.model.ConversationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findByConversationType(ConversationType type);

    Optional<Conversation> findByClassRoom_ClassId(Long classId);

    @Query("SELECT c FROM Conversation c JOIN c.members m WHERE m.user.id = :userId AND m.leftAt IS NULL")
    List<Conversation> findByMemberUserId(@Param("userId") Long userId);
}
