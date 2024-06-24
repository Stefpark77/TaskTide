package com.tasktide.authServices.controller.model.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LogInRequest {

    @NotBlank(message = "The username of the user is not valid!")
    @Size(min = 5, message = "The username length should be at least 5")
    private String username;

    @NotBlank(message = "The password is not valid!")
    @Size(min = 8, message = "The password length should be at least 8")
    private String password;
}
