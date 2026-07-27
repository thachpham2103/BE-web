package com.example.be.web.controller;

import com.example.be.web.base.RestData;
import com.example.be.web.base.VsResponseUtil;
import com.example.be.web.doman.dto.request.chat.ConversationRequestDto;
import com.example.be.web.doman.dto.request.chat.MessageRequestDto;
import com.example.be.web.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "API quản lý cuộc hội thoại và tin nhắn")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/conversations")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Tạo cuộc hội thoại mới")
    public ResponseEntity<RestData<?>> createConversation(
            @Valid @RequestBody ConversationRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return VsResponseUtil.success(HttpStatus.CREATED, chatService.createConversation(dto, userDetails.getUsername()));
    }

    @GetMapping("/conversations")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy danh sách cuộc hội thoại của tôi")
    public ResponseEntity<RestData<?>> getMyConversations(
            @AuthenticationPrincipal UserDetails userDetails) {
        return VsResponseUtil.success(chatService.getMyConversations(userDetails.getUsername()));
    }

    @GetMapping("/conversations/{convoId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy chi tiết cuộc hội thoại")
    public ResponseEntity<RestData<?>> getConversation(@PathVariable Long convoId) {
        return VsResponseUtil.success(chatService.getConversation(convoId));
    }

    @PostMapping("/messages")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Gửi tin nhắn")
    public ResponseEntity<RestData<?>> sendMessage(
            @Valid @RequestBody MessageRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return VsResponseUtil.success(HttpStatus.CREATED, chatService.sendMessage(dto, userDetails.getUsername()));
    }

    @GetMapping("/conversations/{convoId}/messages")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy tin nhắn trong cuộc hội thoại")
    public ResponseEntity<RestData<?>> getMessages(
            @PathVariable Long convoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("sentAt").descending());
        return VsResponseUtil.success(chatService.getMessages(convoId, pageable));
    }

    @DeleteMapping("/messages/{messageId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Xóa mềm tin nhắn")
    public ResponseEntity<RestData<?>> deleteMessage(@PathVariable Long messageId) {
        chatService.deleteMessage(messageId);
        return VsResponseUtil.success("Đã xóa tin nhắn");
    }

    @PostMapping("/messages/{messageId}/read")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Đánh dấu đã đọc tin nhắn")
    public ResponseEntity<RestData<?>> markAsRead(
            @PathVariable Long messageId,
            @AuthenticationPrincipal UserDetails userDetails) {
        chatService.markAsRead(messageId, userDetails.getUsername());
        return VsResponseUtil.success("Đã đánh dấu đọc");
    }

    @PostMapping("/conversations/{convoId}/pin/{messageId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Ghim/Bỏ ghim tin nhắn")
    public ResponseEntity<RestData<?>> togglePin(
            @PathVariable Long convoId,
            @PathVariable Long messageId,
            @AuthenticationPrincipal UserDetails userDetails) {
        chatService.togglePin(convoId, messageId, userDetails.getUsername());
        return VsResponseUtil.success("Đã thay đổi trạng thái ghim");
    }

    @GetMapping("/conversations/{convoId}/pinned")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy danh sách tin nhắn đã ghim")
    public ResponseEntity<RestData<?>> getPinnedMessages(@PathVariable Long convoId) {
        return VsResponseUtil.success(chatService.getPinnedMessages(convoId));
    }

    @GetMapping("/conversations/public")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy danh sách nhóm công khai")
    public ResponseEntity<RestData<?>> getPublicGroups(@AuthenticationPrincipal UserDetails userDetails) {
        return VsResponseUtil.success(chatService.getPublicGroups(userDetails.getUsername()));
    }

    @PostMapping("/conversations/{convoId}/join-request")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Gửi yêu cầu tham gia nhóm")
    public ResponseEntity<RestData<?>> requestJoinGroup(
            @PathVariable Long convoId,
            @AuthenticationPrincipal UserDetails userDetails) {
        chatService.requestJoinGroup(convoId, userDetails.getUsername());
        return VsResponseUtil.success(HttpStatus.CREATED, "Đã gửi yêu cầu tham gia nhóm");
    }

    @GetMapping("/conversations/join-requests/pending")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy danh sách yêu cầu tham gia nhóm đang chờ duyệt")
    public ResponseEntity<RestData<?>> getPendingJoinRequests(@AuthenticationPrincipal UserDetails userDetails) {
        return VsResponseUtil.success(chatService.getPendingJoinRequests(userDetails.getUsername()));
    }

    @PostMapping("/conversations/join-requests/{requestId}/approve")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Duyệt yêu cầu tham gia nhóm")
    public ResponseEntity<RestData<?>> approveJoinRequest(
            @PathVariable Long requestId,
            @AuthenticationPrincipal UserDetails userDetails) {
        chatService.approveJoinRequest(requestId, userDetails.getUsername());
        return VsResponseUtil.success("Đã duyệt yêu cầu");
    }

    @PostMapping("/conversations/join-requests/{requestId}/reject")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Từ chối yêu cầu tham gia nhóm")
    public ResponseEntity<RestData<?>> rejectJoinRequest(
            @PathVariable Long requestId,
            @AuthenticationPrincipal UserDetails userDetails) {
        chatService.rejectJoinRequest(requestId, userDetails.getUsername());
        return VsResponseUtil.success("Đã từ chối yêu cầu");
    }
}
