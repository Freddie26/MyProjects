package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.controller.dto.AuthUserDto;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthUserDto authUserDto) {
        boolean userSaved = userService.registerNewUser(authUserDto);
        return (userSaved)
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким именем уже существует");
    }

    @PostMapping("/users/make-presenter/{userId}")
    public ResponseEntity<?> makePresenter(@PathVariable Long userId) {
        boolean successful = userService.makePresenter(userId);
        HttpStatus httpStatus = successful ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).build();
    }
}
