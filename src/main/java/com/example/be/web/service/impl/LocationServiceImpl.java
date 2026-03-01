package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.location.LocationRequestDto;
import com.example.be.web.doman.dto.response.location.LocationResponseDto;
import com.example.be.web.doman.entity.Location;
import com.example.be.web.doman.mapper.LocationMapper;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.LocationRepository;
import com.example.be.web.service.LocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class LocationServiceImpl implements LocationService {
    private LocationRepository locationRepository;
    private LocationMapper mapper;

    @Override
    public LocationResponseDto createLocation(LocationRequestDto requestDto) {
        Location location = mapper.toEntity(requestDto);
        Location saved = locationRepository.save(location);
        return mapper.toResponseDto(saved);
    }

    @Override
    public LocationResponseDto getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Location.LOCATION_NOT_FOUND,
                        new String[]{id.toString()}
                ));
        return mapper.toResponseDto(location);
    }

    @Override
    public LocationResponseDto updateLocation(Long id, LocationRequestDto requestDto) {
        Location existing = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Location.LOCATION_NOT_FOUND,
                        new String[]{id.toString()}
                ));

        existing.setLocationCode(requestDto.getLocationCode());
        existing.setLatitude(requestDto.getLatitude());
        existing.setLongitude(requestDto.getLongitude());
        existing.setRadiusMeters(requestDto.getRadiusMeters());
        existing.setAddress(requestDto.getAddress());

        Location updated = locationRepository.save(existing);
        return mapper.toResponseDto(updated);    }

    @Override
    public void deleteLocation(Long id) {
        Location existing = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Location.LOCATION_NOT_FOUND,
                        new String[]{id.toString()}
                ));

        locationRepository.delete(existing);
    }

    @Override
    public Page<LocationResponseDto> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable).map(mapper::toResponseDto);
    }
}
