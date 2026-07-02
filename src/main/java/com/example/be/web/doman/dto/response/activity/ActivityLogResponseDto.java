package com.example.be.web.doman.dto.response.activity;

import com.example.be.web.doman.model.ActivityAction;
import com.example.be.web.doman.model.TargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin nhật ký hoạt động")
public class ActivityLogResponseDto {

    @Schema(description = "ID log")
    private Long logId;
    @Schema(description = "ID người thực hiện")
    private Long actorId;
    @Schema(description = "Tên người thực hiện")
    private String actorName;
    @Schema(description = "Hành động")
    private ActivityAction action;
    @Schema(description = "Loại đối tượng")
    private TargetType targetType;
    @Schema(description = "ID đối tượng")
    private Long targetId;
    @Schema(description = "Mô tả")
    private String description;
    @Schema(description = "Địa chỉ IP")
    private String ipAddress;
    @Schema(description = "Thời điểm")
    private LocalDateTime createdAt;
}
