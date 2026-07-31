package com.example.be.web.controller;

import com.example.be.web.service.impl.FaceAIClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/embedding")
@RequiredArgsConstructor
@Tag(name="embedding")
public class FaceAIClientController {

    private final FaceAIClient faceAIClient;
@PostMapping(
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//nó nè phải thêm nó vào để swagger hiểu là nhận file
@PreAuthorize("hasAnyRole('ADMIN','LEADER')")
@Operation(summary = "Upload face image to get embedding")
public double[] getOriginalEmbedding(
        @Parameter(description = "Face image file", required = true)
        @RequestPart("image") MultipartFile file) throws IOException {

    File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
    file.transferTo(tempFile);

    double[] embedding = faceAIClient.getOriginalEmbedding(tempFile);

    tempFile.delete();
    return embedding;
}

}
