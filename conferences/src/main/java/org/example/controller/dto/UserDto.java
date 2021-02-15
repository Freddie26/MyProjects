package org.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @JsonProperty(value = "UserName")
    private String username;

    @JsonProperty(value = "Password")
    private String password;

    @JsonProperty(value = "ConfirmPassword")
    private String confirmPassword;
}
