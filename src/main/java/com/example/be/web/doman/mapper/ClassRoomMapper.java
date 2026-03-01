package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.request.classRoom.ClassRoomRequestDto;
import com.example.be.web.doman.dto.response.classRoom.ClassRoomResponseDto;
import com.example.be.web.doman.entity.ClassRoom;
import com.example.be.web.doman.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClassRoomMapper {

    @Mapping(target = "teacherId", ignore = true)
    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    ClassRoom toEntity(ClassRoomRequestDto classRoomRequestDto);

//    @Mapping(source = "location.locationId", target = "locationId")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "teacher.username", target = "teacherName")
    @Mapping(target = "locationIds", expression = "java(mapLocations(classRoom.getLocations()))")
    ClassRoomResponseDto toResponse(ClassRoom classRoom);

    List<ClassRoomResponseDto> toResponses(List<ClassRoom> classRooms);

    default List<Long> mapLocations(Set<Location> locations) {
        if (locations == null) return new ArrayList<>();
        return locations.stream().map(Location::getLocationId).toList();
    }

}
