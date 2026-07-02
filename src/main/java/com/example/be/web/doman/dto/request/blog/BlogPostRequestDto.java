package com.example.be.web.doman.dto.request.blog;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request body tạo/cập nhật bài viết blog")
public class BlogPostRequestDto {

    @NotBlank(message = "Tiêu đề không được để trống")
    @Schema(description = "Tiêu đề bài viết", example = "Hướng dẫn sử dụng hệ thống")
    private String title;

    @Schema(description = "Nội dung bài viết")
    private String content;

    @Schema(description = "URL ảnh thumbnail")
    private String thumbnailUrl;

    @Schema(description = "Danh sách tag ID")
    private List<Long> tagIds;
}
