package com.example.be.web.controller;

import com.example.be.web.base.RestApiV1;
import com.example.be.web.base.VsResponseUtil;
import com.example.be.web.constant.ResponseMessage;
import com.example.be.web.constant.UrlConstant;
import com.example.be.web.doman.dto.request.user.UserCreateDto;
import com.example.be.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RestApiV1
@Validated

public class UserController {

    private final UserService userService;


    @Tag(name="admin_leader")
    @GetMapping
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        return VsResponseUtil.success(userService.getUserById(userId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LEADER')")
    @Tag(name="admin_leader")
    @Operation(summary = "API AMIN, LEADER")
    @PostMapping(UrlConstant.User.CREATE_USER)
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateDto createDto){
        return VsResponseUtil.success(userService.createUser(createDto));
    }

    @Tag(name="admin_leader")
    @DeleteMapping(UrlConstant.User.DELETE_USER)
    @Operation(summary = "API delete user by id", description = "Admin / Leader")
    public ResponseEntity<?> deleteUsers(@PathVariable Long id){
            userService.deleteUsers(id);
            return VsResponseUtil.success(ResponseMessage.User.USER_DELETE);
    }

}
