package com.example.be.web.repository;

import com.example.be.web.doman.entity.SavedPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavedPostRepository extends JpaRepository<SavedPost, Long> {

    Page<SavedPost> findByUser_Id(Long userId, Pageable pageable);

    Optional<SavedPost> findByUser_IdAndPost_BlogPostId(Long userId, Long postId);

    boolean existsByUser_IdAndPost_BlogPostId(Long userId, Long postId);
}
