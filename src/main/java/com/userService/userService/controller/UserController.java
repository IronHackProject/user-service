package com.userService.userService.controller;

import com.userService.userService.dto.CreateUserRequestDTO;
import com.userService.userService.dto.FindUserByEmailRequestDTO;
import com.userService.userService.model.User;
import com.userService.userService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserRequestDTO dto) {
        return userService.createUser(dto);
    }

    @GetMapping("/findUser")
    public ResponseEntity<?> findUser(@RequestBody @Valid FindUserByEmailRequestDTO dto) {
        return userService.findUserByEmail(dto);
    }

    // use FeingClient
    @GetMapping("/find/{email}")
    public ResponseEntity<User> findUserByEmailClient(@PathVariable String email) {
        return userService.findUserByEmailOrderService(email);
    }


}
