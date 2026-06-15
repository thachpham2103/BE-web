package com.example.be.web.repository;

import com.example.be.web.doman.entity.Document;
import com.example.be.web.doman.entity.DocumentDownloadHistory;
import com.example.be.web.doman.entity.DocumentViewHistory;
import com.example.be.web.doman.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentDownloadHistoryRepository extends JpaRepository<DocumentDownloadHistory, Long> {

    List<DocumentDownloadHistory> findAllByUserOrderByDownloadedAtDesc(User user);

    List<DocumentDownloadHistory> findAllByDocumentOrderByDownloadedAtDesc(Document document);

    long countByDocument(Document document);

    long countByUser(User user);
}