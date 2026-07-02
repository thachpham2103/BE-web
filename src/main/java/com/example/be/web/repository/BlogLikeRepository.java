package com.example.be.web.repository;

import com.example.be.web.doman.entity.BlogLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogLikeRepository extends JpaRepository<BlogLike, Long> {

    boolean existsByPost_BlogPostIdAndUser_Id(Long postId, Long userId);

    Optional<BlogLike> findByPost_BlogPostIdAndUser_Id(Long postId, Long userId);

    long countByPost_BlogPostId(Long postId);
}
