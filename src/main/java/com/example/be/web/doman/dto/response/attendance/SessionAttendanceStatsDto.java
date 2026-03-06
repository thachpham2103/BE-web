package com.example.be.web.doman.dto.response.attendance;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SessionAttendanceStatsDto {
    private Long sessionId;
    private String title;
    private long presentCount;
    private long totalCount;
}

