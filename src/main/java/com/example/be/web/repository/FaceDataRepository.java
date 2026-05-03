package com.example.be.web.repository;

import com.example.be.web.doman.entity.FaceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceDataRepository extends JpaRepository<FaceData, Long> {
}