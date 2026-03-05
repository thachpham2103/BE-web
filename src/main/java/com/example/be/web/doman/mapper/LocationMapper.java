package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.request.location.LocationRequestDto;
import com.example.be.web.doman.dto.response.auth.LoginResponseDto;
import com.example.be.web.doman.dto.response.location.LocationResponseDto;
import com.example.be.web.doman.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LocationMapper {


//    @Mapping(target = "locationCode", ignore = true)
//    @Mapping(target = "latitude", ignore = true)
//    @Mapping(target = "longitude", ignore = true)
//    @Mapping(target = "radiusMeters", ignore = true)
    Location toEntity(LocationRequestDto location);

    LocationResponseDto toResponseDto(Location location);

    List<LocationResponseDto> toResponseDtoList(List<Location> locations);
}
