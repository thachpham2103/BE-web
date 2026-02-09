package com.example.be.web.repository;

import com.example.be.web.doman.entity.Location;

import java.util.Optional;

public interface LocationRepository {
        Optional<Location> findById(Long id);
}
