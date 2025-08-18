package com.example.be.web.doman.request;

import lombok.*;
import org.apache.commons.collections4.Get;

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
