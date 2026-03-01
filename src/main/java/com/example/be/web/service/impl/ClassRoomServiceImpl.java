package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.classRoom.ClassRoomRequestDto;
import com.example.be.web.doman.dto.response.classRoom.ClassRoomResponseDto;
import com.example.be.web.doman.entity.ClassRoom;
import com.example.be.web.doman.entity.Location;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.mapper.ClassRoomMapper;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.ClassRepository;
import com.example.be.web.repository.LocationRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.security.UserPrincipal;
import com.example.be.web.service.ClassRoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClassRoomServiceImpl implements ClassRoomService {
    private final ClassRepository classRepository;
    private final ClassRoomMapper mapper;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;


    @Override
    public ClassRoomResponseDto createClassRoom(ClassRoomRequestDto requestDto) {
        ClassRoom classRoom = mapper.toEntity(requestDto);

        // lấy user đang đăng nhập từ SecurityContext
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User creator = userRepository.findById(principal.getId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND_ID, new String[]{principal.getId().toString()}));
        classRoom.setTeacher(creator);

        // xử lý Location
//        Location location = locationRepository.findById(requestDto.getLocationId())
//                .orElseThrow(() -> new NotFoundException( ErrorMessage.Location.LOCATION_NOT_FOUND, new String[]{requestDto.getLocationId().toString()} ));
//        classRoom.setLocation(location);
        Set<Location> locations = new HashSet<>(locationRepository.findAllById(requestDto.getLocationIds()));
        classRoom.setLocations(locations);

        // Lưu vào DB
        ClassRoom savedClassRoom = classRepository.save(classRoom);

        // Trả về DTO response
        return mapper.toResponse(savedClassRoom);
    }

    @Override
    public ClassRoomResponseDto getClassRoomById(Long classId) {
        ClassRoom classRoom = classRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.ClassRoom.CLASS_NOT_FOUND,
                        new String[]{classId.toString()}
                ));

        return mapper.toResponse(classRoom);
    }

    @Override
    public ClassRoomResponseDto updateClassRoom(Long classId, ClassRoomRequestDto requestDto) {
        // 1. Tìm classRoom theo id
        ClassRoom existing = classRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.ClassRoom.CLASS_NOT_FOUND,
                        new String[]{classId.toString()}
                ));

        // 2. Cập nhật các field cơ bản
        existing.setTitle(requestDto.getTitle());
        existing.setDescription(requestDto.getDescription());
        existing.setStartDate(requestDto.getStartDate());
        existing.setEndDate(requestDto.getEndDate());

        // 3. Xử lý teacher (nếu cho phép đổi teacher)
        User teacher = userRepository.findById(requestDto.getTeacherId())
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.User.USER_NOT_FOUND_ID,
                        new String[]{requestDto.getTeacherId().toString()}
                ));
        existing.setTeacher(teacher);

        // 4. Xử lý locations
        Set<Location> locations = new HashSet<>(locationRepository.findAllById(requestDto.getLocationIds()));
        existing.setLocations(locations);

        // 5. Lưu lại entity đã cập nhật
        ClassRoom updated = classRepository.save(existing);

        // 6. Trả về DTO response
        return mapper.toResponse(updated);
    }


    @Override
    public void deleteClassRoom(Long classId) {
        // 1. Kiểm tra xem classRoom có tồn tại không
        ClassRoom existing = classRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.ClassRoom.CLASS_NOT_FOUND,
                        new String[]{classId.toString()}
                ));

        // 2. Xóa classRoom
        classRepository.delete(existing);
    }

    @Override
    public Page<ClassRoomResponseDto> getAllClassRooms(Pageable pageable) {
        // Lấy danh sách ClassRoom từ DB theo phân trang
        Page<ClassRoom> classRooms = classRepository.findAll(pageable);

        // Map từng entity sang DTO
        return classRooms.map(mapper::toResponse);
    }

}
