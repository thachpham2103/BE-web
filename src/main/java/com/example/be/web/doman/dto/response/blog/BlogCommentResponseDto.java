package com.example.be.web.doman.dto.response.blog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin bình luận")
public class BlogCommentResponseDto {

    @Schema(description = "ID bình luận")
    private Long commentId;
    @Schema(description = "ID bài viết")
    private Long postId;
    @Schema(description = "ID người bình luận")
    private Long userId;
    @Schema(description = "Tên người bình luận")
    private String userName;
    @Schema(description = "Avatar người bình luận")
    private String userAvatarUrl;
    @Schema(description = "ID bình luận cha")
    private Long parentId;
    @Schema(description = "Nội dung bình luận")
    private String content;
    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;
    @Schema(description = "Danh sách bình luận con")
    private List<BlogCommentResponseDto> replies;
}
