package com.example.be.web.doman.dto.response.blog;

import com.example.be.web.doman.model.BlogPostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin bài viết blog")
public class BlogPostResponseDto {

    @Schema(description = "ID bài viết")
    private Long blogPostId;
    @Schema(description = "ID tác giả")
    private Long authorId;
    @Schema(description = "Tên tác giả")
    private String authorName;
    @Schema(description = "Avatar tác giả")
    private String authorAvatarUrl;
    @Schema(description = "Tiêu đề")
    private String title;
    @Schema(description = "Nội dung")
    private String content;
    @Schema(description = "Ảnh thumbnail")
    private String thumbnailUrl;
    @Schema(description = "Trạng thái")
    private BlogPostStatus status;
    @Schema(description = "Số lượt xem")
    private Integer viewCount;
    @Schema(description = "Số lượt thích")
    private Long likeCount;
    @Schema(description = "Số bình luận")
    private Long commentCount;
    @Schema(description = "Danh sách tag")
    private List<String> tagNames;
    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;
    @Schema(description = "Ngày cập nhật")
    private LocalDateTime updatedAt;
}
