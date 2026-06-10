package com.example.be.web.repository;


import com.example.be.web.doman.entity.Document;
import com.example.be.web.doman.entity.DocumentNote;
import com.example.be.web.doman.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentNoteRepository extends JpaRepository<DocumentNote, Long> {

    List<DocumentNote> findAllByUserOrderByUpdatedAtDesc(User user);

    List<DocumentNote> findAllByUserAndDocumentOrderByUpdatedAtDesc(User user, Document document);

    Optional<DocumentNote> findByNoteIdAndUser(Long noteId, User user);

    void deleteByNoteIdAndUser(Long noteId, User user);
}