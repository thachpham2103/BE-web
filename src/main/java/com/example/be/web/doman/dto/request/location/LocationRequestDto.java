package com.example.be.web.doman.dto.request.location;

import com.example.be.web.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LocationRequestDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String locationCode;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private Double latitude;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private Double longitude;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private Double radiusMeters;

    private String address;

}
