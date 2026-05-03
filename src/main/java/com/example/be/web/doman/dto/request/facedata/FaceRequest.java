package com.example.be.web.doman.dto.request.facedata;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FaceRequest {
    private Long userId;
    private MultipartFile image;
}
