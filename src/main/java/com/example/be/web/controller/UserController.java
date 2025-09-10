package com.example.be.web.controller;

import com.example.be.web.base.RestApiV1;
import com.example.be.web.base.VsResponseUtil;
import com.example.be.web.constant.ResponseMessage;
import com.example.be.web.constant.UrlConstant;
import com.example.be.web.doman.request.UserCreateDto;
import com.example.be.web.service.UserService;
import com.example.be.web.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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


    @Tag(name="admin_leader")
    @PostMapping(UrlConstant.User.CREATE_USER)
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateDto createDto){
        return VsResponseUtil.success(userService.createUser(createDto));
    }

    @Tag(name="admin_leader")
    @DeleteMapping(UrlConstant.User.DELETE_USER)
    public ResponseEntity<?> deleteUsers(@PathVariable Long id){
            userService.deleteUsers(id);
            return VsResponseUtil.success(ResponseMessage.User.USER_DELETE);
    }

}
