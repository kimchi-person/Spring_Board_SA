package com.sparta.spring_board_sa.controller;

import com.sparta.spring_board_sa.dto.ResponseDto;
import com.sparta.spring_board_sa.dto.UserRequestDto;
import com.sparta.spring_board_sa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto<String> signup(@RequestBody UserRequestDto userRequestDto) {
        return userService.signup(userRequestDto);
    }

    @PostMapping("login")
    public ResponseDto<String> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {

        return userService.login(userRequestDto, response);
    }


}
