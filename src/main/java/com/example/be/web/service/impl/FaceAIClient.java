package com.example.be.web.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

@Service
public class FaceAIClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public double[] getEmbedding(String base64) {

        String url = "http://localhost:5000/embedding";

        Map<String, String> body = new HashMap<>();
        body.put("image", base64);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, body, Map.class);

        List<Double> list = (List<Double>) response.getBody().get("embedding");

        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }
}