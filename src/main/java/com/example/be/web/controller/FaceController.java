package com.example.be.web.controller;
import com.example.be.web.doman.dto.request.attendance.AttendanceSessionRequestDto;
import com.example.be.web.doman.dto.request.facedata.FaceRequest;
import com.example.be.web.doman.dto.response.attendance.AttendanceSessionResponseDto;
import com.example.be.web.doman.dto.response.facedata.FaceResponse;
import com.example.be.web.service.AttendanceSessionService;
import com.example.be.web.service.impl.FaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/face")
@RequiredArgsConstructor
public class FaceController {

    private final FaceService faceService;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody FaceRequest req) {

        faceService.register(req.getUserId(), req.getImage());

        return ResponseEntity.ok("Registered");
    }

    // RECOGNIZE
    @PostMapping("/recognize")
    public ResponseEntity<FaceResponse> recognize(@RequestBody FaceRequest req) {

        return ResponseEntity.ok(
                faceService.recognize(req.getImage())
        );
    }
}
