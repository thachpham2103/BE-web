package com.example.be.web.doman.dto.response.facedata;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter

public class FaceResponse {

    private String name;
    private double confidence;
    private String message;
}
