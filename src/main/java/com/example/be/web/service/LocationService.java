package com.example.be.web.service;

import com.example.be.web.doman.dto.request.location.LocationRequestDto;
import com.example.be.web.doman.dto.response.location.LocationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface LocationService {
    LocationResponseDto createLocation(LocationRequestDto requestDto);
    LocationResponseDto getLocationById(Long id);
    LocationResponseDto updateLocation(Long id, LocationRequestDto requestDto);
    void deleteLocation(Long id);
    Page<LocationResponseDto> getAllLocations(Pageable pageable);
}
