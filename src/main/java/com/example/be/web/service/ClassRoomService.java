package com.example.be.web.service;

import com.example.be.web.doman.dto.request.classRoom.ClassRoomRequestDto;
import com.example.be.web.doman.dto.response.classRoom.ClassRoomResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassRoomService {
    ClassRoomResponseDto createClassRoom(ClassRoomRequestDto requestDto);
    ClassRoomResponseDto getClassRoomById(Long classId);
    ClassRoomResponseDto updateClassRoom(Long classId, ClassRoomRequestDto requestDto);
    void deleteClassRoom(Long classId);
    Page<ClassRoomResponseDto> getAllClassRooms(Pageable pageable);
//    List<ClassRoomResponseDto> getAllClassRooms();

}
