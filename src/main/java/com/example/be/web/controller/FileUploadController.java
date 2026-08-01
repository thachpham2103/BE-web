package com.example.be.web.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.be.web.base.RestData;
import com.example.be.web.base.VsResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Tag(name = "File Upload", description = "API cho upload file")
public class FileUploadController {

    private final Cloudinary cloudinary;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a file")
    public ResponseEntity<RestData<?>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String fileUrl = uploadResult.get("secure_url").toString();

            return VsResponseUtil.success(fileUrl);
        } catch (IOException ex) {
            return ResponseEntity.internalServerError().body(new RestData<>("Lỗi upload file: " + ex.getMessage()));
        }
    }
}
