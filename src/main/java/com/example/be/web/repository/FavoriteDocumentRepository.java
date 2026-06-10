package com.example.be.web.repository;

import com.example.be.web.doman.entity.Document;
import com.example.be.web.doman.entity.FavoriteDocument;
import com.example.be.web.doman.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteDocumentRepository extends JpaRepository<FavoriteDocument, Long> {

    boolean existsByUserAndDocument(User user, Document document);

    Optional<FavoriteDocument> findByUserAndDocument(User user, Document document);

    List<FavoriteDocument> findAllByUserOrderByCreatedAtDesc(User user);
}