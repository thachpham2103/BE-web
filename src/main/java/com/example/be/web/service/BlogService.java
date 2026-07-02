package com.example.be.web.service;

import com.example.be.web.doman.dto.request.blog.BlogCommentRequestDto;
import com.example.be.web.doman.dto.request.blog.BlogPostRequestDto;
import com.example.be.web.doman.dto.response.blog.BlogCommentResponseDto;
import com.example.be.web.doman.dto.response.blog.BlogPostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    BlogPostResponseDto createPost(BlogPostRequestDto dto, String username);
    Page<BlogPostResponseDto> getPosts(Pageable pageable);
    BlogPostResponseDto getPost(Long id);
    void deletePost(Long id);

    BlogCommentResponseDto addComment(Long postId, BlogCommentRequestDto dto, String username);
    Page<BlogCommentResponseDto> getComments(Long postId, Pageable pageable);

    void toggleLike(Long postId, String username);
    void toggleSave(Long postId, String username);
    Page<BlogPostResponseDto> getSavedPosts(String username, Pageable pageable);
}
