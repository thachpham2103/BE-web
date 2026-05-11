package com.example.be.web.doman.dto.response.facedata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmbeddingResultDto {
    private double[] embedding;
    private String status; // "Success", "Spoof detected", "No face detected"
}

