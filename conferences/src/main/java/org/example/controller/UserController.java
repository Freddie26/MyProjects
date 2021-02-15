package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.controller.dto.AuthUserDto;
import org.example.controller.dto.UserDto;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@AllArgsConstructor
@RestController("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody AuthUserDto authUserDto) {
        if (!Objects.equals(authUserDto.getPassword(), authUserDto.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пароли не совпадают");
        }

        boolean userSaved = userService.saveUser(authUserDto);
        return (userSaved)
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким именем уже существует");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long id) {
        boolean deleted = userService.deleteUser(id);
        return ResponseEntity.status(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND).build();
    }
}
