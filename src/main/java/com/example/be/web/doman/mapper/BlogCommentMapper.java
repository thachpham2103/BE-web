package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.blog.BlogCommentResponseDto;
import com.example.be.web.doman.entity.BlogComment;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BlogCommentMapper {

    @Mapping(source = "post.blogPostId", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "user.avatarUrl", target = "userAvatarUrl")
    @Mapping(source = "parent.commentId", target = "parentId")
    @Mapping(source = "createDate", target = "createdAt")
    @Mapping(target = "replies", ignore = true)
    BlogCommentResponseDto toResponse(BlogComment entity);
}
