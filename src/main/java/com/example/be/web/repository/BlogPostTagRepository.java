package com.example.be.web.repository;

import com.example.be.web.doman.entity.BlogPostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostTagRepository extends JpaRepository<BlogPostTag, Long> {

    List<BlogPostTag> findByPost_BlogPostId(Long postId);

    void deleteByPost_BlogPostIdAndTag_Id(Long postId, Long tagId);
}
