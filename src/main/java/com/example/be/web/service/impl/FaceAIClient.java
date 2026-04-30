package com.example.be.web.service.impl;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

@Service
public class FaceAIClient {

    private final RestTemplate restTemplate = new RestTemplate();

//    public double[] getEmbedding(String base64) {
//
//        String url = "http://localhost:5000/embedding";
//
//        Map<String, String> body = new HashMap<>();
//        body.put("image", base64);
//
//        ResponseEntity<Map> response =
//                restTemplate.postForEntity(url, body, Map.class);
//
//        List<Double> list = (List<Double>) response.getBody().get("embedding");
//
//        return list.stream().mapToDouble(Double::doubleValue).toArray();
//    }
    public double[] getOriginalEmbedding(File imageFile) {
        String url = "http://localhost:5000/original_embedding";

        FileSystemResource resource = new FileSystemResource(imageFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, requestEntity, Map.class);

        List<Double> list = (List<Double>) response.getBody().get("embedding");
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public double[] getAttendanceEmbedding(File imageFile) {
        String url = "http://localhost:6000/attendance_embedding";

        FileSystemResource resource = new FileSystemResource(imageFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, requestEntity, Map.class);

        List<Double> list = (List<Double>) response.getBody().get("embedding");
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }


}