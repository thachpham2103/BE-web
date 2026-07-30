package com.example.be.web.doman.dto.request.classRoom;

import com.example.be.web.constant.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClassRoomRequestDto {
    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private Long teacherId;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private String title;

    private String description;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private LocalDate startDate;

    private LocalDate endDate;

    private List<Long> locationIds;

    private java.math.BigDecimal tuitionFee;
}
