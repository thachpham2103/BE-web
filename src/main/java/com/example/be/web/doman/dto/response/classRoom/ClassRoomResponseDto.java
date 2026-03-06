package com.example.be.web.doman.dto.response.classRoom;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClassRoomResponseDto {
    private Long  classId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    private Long teacherId;
    private String teacherName;

//    private Long locationId;
    private List<Long> locationIds;
}
