package com.example.be.web.doman.dto.request.blog;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request body tạo bình luận")
public class BlogCommentRequestDto {

    @NotBlank(message = "Nội dung bình luận không được để trống")
    @Schema(description = "Nội dung bình luận")
    private String content;

    @Schema(description = "ID bình luận cha (nullable = bình luận gốc)")
    private Long parentId;
}
