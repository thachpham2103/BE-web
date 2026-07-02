package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.blog.BlogPostResponseDto;
import com.example.be.web.doman.entity.BlogPost;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BlogPostMapper {

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.fullName", target = "authorName")
    @Mapping(source = "author.avatarUrl", target = "authorAvatarUrl")
    @Mapping(source = "createDate", target = "createdAt")
    @Mapping(source = "lastModifiedDate", target = "updatedAt")
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "commentCount", ignore = true)
    @Mapping(target = "tagNames", ignore = true)
    BlogPostResponseDto toResponse(BlogPost entity);
}
