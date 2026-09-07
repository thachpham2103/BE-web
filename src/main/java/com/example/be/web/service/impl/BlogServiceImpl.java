package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.blog.BlogCommentRequestDto;
import com.example.be.web.doman.dto.request.blog.BlogPostRequestDto;
import com.example.be.web.doman.dto.response.blog.BlogCommentResponseDto;
import com.example.be.web.doman.dto.response.blog.BlogPostResponseDto;
import com.example.be.web.doman.entity.*;
import com.example.be.web.doman.mapper.BlogCommentMapper;
import com.example.be.web.doman.mapper.BlogPostMapper;
import com.example.be.web.doman.model.BlogPostStatus;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.*;
import com.example.be.web.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogServiceImpl implements BlogService {

    private final BlogPostRepository blogPostRepository;
    private final BlogCommentRepository blogCommentRepository;
    private final BlogLikeRepository blogLikeRepository;
    private final BlogPostTagRepository blogPostTagRepository;
    private final SavedPostRepository savedPostRepository;
    private final UserRepository userRepository;
    private final BlogPostMapper blogPostMapper;
    private final BlogCommentMapper blogCommentMapper;

    @Override
    public BlogPostResponseDto createPost(BlogPostRequestDto dto, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        BlogPost post = BlogPost.builder()
                .author(author)
                .title(dto.getTitle())
                .content(dto.getContent())
                .thumbnailUrl(dto.getThumbnailUrl())
                .status(BlogPostStatus.PUBLISHED)
                .build();
        blogPostRepository.save(post);
        return blogPostMapper.toResponse(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlogPostResponseDto> getPosts(Pageable pageable) {
        return blogPostRepository.findByStatus(BlogPostStatus.PUBLISHED, pageable)
                .map(post -> {
                    BlogPostResponseDto dto = blogPostMapper.toResponse(post);
                    dto.setLikeCount(blogLikeRepository.countByPost_BlogPostId(post.getBlogPostId()));
                    dto.setCommentCount(blogCommentRepository.countByPost_BlogPostId(post.getBlogPostId()));
                    dto.setTagNames(blogPostTagRepository.findByPost_BlogPostId(post.getBlogPostId())
                            .stream().map(t -> t.getTag().getName()).collect(Collectors.toList()));
                    return dto;
                });
    }

    @Override
    public BlogPostResponseDto getPost(Long id) {
        BlogPost post = blogPostRepository.findByBlogPostIdAndStatusNot(id, BlogPostStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_BLANK_FIELD.toLowerCase()));
        post.setViewCount(post.getViewCount() + 1);
        blogPostRepository.save(post);

        BlogPostResponseDto dto = blogPostMapper.toResponse(post);
        dto.setLikeCount(blogLikeRepository.countByPost_BlogPostId(id));
        dto.setCommentCount(blogCommentRepository.countByPost_BlogPostId(id));
        dto.setTagNames(blogPostTagRepository.findByPost_BlogPostId(id)
                .stream().map(t -> t.getTag().getName()).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public void deletePost(Long id) {
        BlogPost post = blogPostRepository.findByBlogPostIdAndStatusNot(id, BlogPostStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Blog.BLOG_NOT_FOUND));
        post.setStatus(BlogPostStatus.DELETED);
        blogPostRepository.save(post);
    }

    @Override
    public BlogCommentResponseDto addComment(Long postId, BlogCommentRequestDto dto, String username) {
        BlogPost post = blogPostRepository.findByBlogPostIdAndStatusNot(postId, BlogPostStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Blog.BLOG_NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND));

        BlogComment comment = BlogComment.builder()
                .post(post)
                .user(user)
                .content(dto.getContent())
                .build();

        if (dto.getParentId() != null) {
            BlogComment parent = blogCommentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Blog.COMMENT_NOT_FOUND));
            comment.setParent(parent);
        }

        blogCommentRepository.save(comment);
        return blogCommentMapper.toResponse(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlogCommentResponseDto> getComments(Long postId, Pageable pageable) {
        return blogCommentRepository.findByPost_BlogPostIdAndParentIsNull(postId, pageable)
                .map(comment -> {
                    BlogCommentResponseDto dto = blogCommentMapper.toResponse(comment);
                    dto.setReplies(blogCommentRepository.findByParent_CommentId(comment.getCommentId())
                            .stream().map(blogCommentMapper::toResponse).collect(Collectors.toList()));
                    return dto;
                });
    }

    @Override
    public void toggleLike(Long postId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND));

        var existing = blogLikeRepository.findByPost_BlogPostIdAndUser_Id(postId, user.getId());
        if (existing.isPresent()) {
            blogLikeRepository.delete(existing.get());
        } else {
            BlogPost post = blogPostRepository.findByBlogPostIdAndStatusNot(postId, BlogPostStatus.DELETED)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Blog.BLOG_NOT_FOUND));
            blogLikeRepository.save(BlogLike.builder().post(post).user(user).build());
        }
    }

    @Override
    public void toggleSave(Long postId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND));

        var existing = savedPostRepository.findByUser_IdAndPost_BlogPostId(user.getId(), postId);
        if (existing.isPresent()) {
            savedPostRepository.delete(existing.get());
        } else {
            BlogPost post = blogPostRepository.findByBlogPostIdAndStatusNot(postId, BlogPostStatus.DELETED)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Blog.BLOG_NOT_FOUND));
            savedPostRepository.save(SavedPost.builder().user(user).post(post).build());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlogPostResponseDto> getSavedPosts(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND));
        return savedPostRepository.findByUser_Id(user.getId(), pageable)
                .map(sp -> blogPostMapper.toResponse(sp.getPost()));
    }
}
