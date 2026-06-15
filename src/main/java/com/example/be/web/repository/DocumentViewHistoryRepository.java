package com.example.be.web.repository;


import com.example.be.web.doman.entity.Document;
import com.example.be.web.doman.entity.DocumentViewHistory;
import com.example.be.web.doman.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentViewHistoryRepository extends JpaRepository<DocumentViewHistory, Long> {

    Optional<DocumentViewHistory> findByUserAndDocument(User user, Document document);

    List<DocumentViewHistory> findAllByUserOrderByViewedAtDesc(User user);

    List<DocumentViewHistory> findAllByDocumentOrderByViewedAtDesc(Document document);

    boolean existsByUserAndDocument(User user, Document document);

}