package com.example.be.web.repository;

import com.example.be.web.doman.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
//        Optional<Location> findById(Long id);
}
