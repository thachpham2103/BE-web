package com.example.be.web.doman.dto.response.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalResponse {

    private Long totalClass;
    private Long totalAdmin;
    private Long totalUser;

}
