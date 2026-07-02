package com.example.be.web.repository;

import com.example.be.web.doman.entity.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {

    Page<BlogComment> findByPost_BlogPostIdAndParentIsNull(Long postId, Pageable pageable);

    List<BlogComment> findByParent_CommentId(Long parentId);

    long countByPost_BlogPostId(Long postId);
}
