package com.example.be.web.doman.dto.response.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CommonResponseDto {

    private Boolean status;

    private String message;
}
