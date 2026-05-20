package com.example.be.web.controller;

import com.example.be.web.doman.dto.request.location.LocationRequestDto;
import com.example.be.web.doman.dto.response.location.LocationResponseDto;
import com.example.be.web.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Tag(name = "Location")
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LEADER', 'USER')")
    @Operation(summary = "API get all locations with pagination")
    public Page<LocationResponseDto> getAllLocations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        return locationService.getAllLocations(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LEADER', 'USER')")
    @Operation(summary = "API get location by id")
    public LocationResponseDto getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LEADER')")
    @Operation(summary = "API create new location")
    public LocationResponseDto createLocation(@RequestBody LocationRequestDto requestDto) {
        return locationService.createLocation(requestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LEADER')")
    @Operation(summary = "API update location by id")
    public LocationResponseDto updateLocation(
            @PathVariable Long id,
            @RequestBody LocationRequestDto requestDto
    ) {
        return locationService.updateLocation(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LEADER')")
    @Operation(summary = "API delete location by id")
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }
}