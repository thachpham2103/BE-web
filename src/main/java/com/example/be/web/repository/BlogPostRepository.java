package com.example.be.web.repository;

import com.example.be.web.doman.entity.BlogPost;
import com.example.be.web.doman.model.BlogPostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogPostRepository
        extends JpaRepository<BlogPost, Long>, JpaSpecificationExecutor<BlogPost> {

    Optional<BlogPost> findByBlogPostIdAndStatusNot(Long id, BlogPostStatus status);

    Page<BlogPost> findByAuthor_IdAndStatusNot(Long authorId, BlogPostStatus status, Pageable pageable);

    Page<BlogPost> findByStatusNot(BlogPostStatus status, Pageable pageable);

    Page<BlogPost> findByStatus(BlogPostStatus status, Pageable pageable);
}
