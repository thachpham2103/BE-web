package com.example.be.web.doman.dto.request.attendance;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.model.RecordStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AttendanceRecordRequestDto {

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private LocalDateTime checkinTime;

//    @NotBlank(message = "Note không được để trống")
    private String note;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private Boolean resultFace;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private Double gpsLatitude;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private Double gpsLongitude;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private RecordStatus recordStatus; // có thể dùng Enum string

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private Long userId;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private Long attendanceSessionId;
}

