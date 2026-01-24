package com.example.be.web.doman.dto.request.auth;

import com.example.be.web.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class LoginRequestDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String username;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String password;


}
