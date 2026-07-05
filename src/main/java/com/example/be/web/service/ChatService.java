package com.example.be.web.service;

import com.example.be.web.doman.dto.request.chat.ConversationRequestDto;
import com.example.be.web.doman.dto.request.chat.MessageRequestDto;
import com.example.be.web.doman.dto.response.chat.ConversationResponseDto;
import com.example.be.web.doman.dto.response.chat.MessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatService {
    ConversationResponseDto createConversation(ConversationRequestDto dto, String username);
    List<ConversationResponseDto> getMyConversations(String username);
    ConversationResponseDto getConversation(Long convoId);

    MessageResponseDto sendMessage(MessageRequestDto dto, String username);
    Page<MessageResponseDto> getMessages(Long convoId, Pageable pageable);
    void deleteMessage(Long messageId);

    void markAsRead(Long messageId, String username);
    void togglePin(Long convoId, Long messageId, String username);
    List<MessageResponseDto> getPinnedMessages(Long convoId);

    List<ConversationResponseDto> getPublicGroups(String username);
    void requestJoinGroup(Long convoId, String username);
    List<com.example.be.web.doman.dto.response.chat.ConversationJoinRequestResponseDto> getPendingJoinRequests(String username);
    void approveJoinRequest(Long requestId, String username);
    void rejectJoinRequest(Long requestId, String username);
}
