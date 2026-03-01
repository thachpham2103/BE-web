package com.example.be.web.doman.dto.response.location;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationResponseDto {

    private Long locationId;
    private String locationCode;
    private Double latitude;
    private Double longitude;
    private Double radiusMeters;
    private String address;

}
