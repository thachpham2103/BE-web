package com.example.be.web.service.impl;


import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.response.facedata.FaceResponse;
import com.example.be.web.doman.entity.FaceData;
import com.example.be.web.doman.entity.User;
import com.example.be.web.repository.FaceDataRepository;
import com.example.be.web.repository.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FaceService {

    private final FaceDataRepository faceRepo;
    private final UserRepository userRepo;
    private final FaceAIClient faceAIClient;

    private final Gson gson = new Gson();

    // ===============================
    // REGISTER
    // ===============================
    public void register(Long userId, String base64) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException(ErrorMessage.User.ERR_NOT_FOUND));

        double[] embedding = faceAIClient.getEmbedding(base64);

        FaceData face = FaceData.builder()
                .faceEncoding(gson.toJson(embedding))
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        faceRepo.save(face);
    }

    // ===============================
    // RECOGNIZE
    // ===============================
    public FaceResponse recognize(String base64) {

        double[] input = faceAIClient.getEmbedding(base64);

        List<FaceData> all = faceRepo.findAll();

        double best = 0;
        User bestUser = null;

        for (FaceData f : all) {

            double[] db = gson.fromJson(f.getFaceEncoding(), double[].class);

            double score = cosine(input, db);

            if (score > best) {
                best = score;
                bestUser = f.getUser();
            }
        }

        String name = (best > 0.6 && bestUser != null)
                ? bestUser.getUsername()
                : "Unknown";

        return FaceResponse.builder()
                .name(name)
                .confidence(best)
                .build();
    }

    // ===============================
    // COSINE
    // ===============================
    private double cosine(double[] a, double[] b) {

        double dot = 0, normA = 0, normB = 0;

        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }

        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}