package com.example.be.web.doman.dto.request.mail;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VerifyCodeDto {

    private String code;
    private String email;
    private String newPassword;

}
