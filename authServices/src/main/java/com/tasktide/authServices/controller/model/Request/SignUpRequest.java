package com.tasktide.authServices.controller.model.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "The first name of the user is not valid!")
    private String firstName;

    @NotBlank(message = "The last name of the user is not valid!")
    private String lastName;

    @NotBlank(message = "The username of the user is not valid!")
    @Size(min = 5, message = "The username length should be at least 5")
    private String username;

    @NotNull(message = "The email of the user is not valid!")
    @Pattern(regexp = "^[a-zA-Z]+[a-zA-Z0-9.]*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",
            message = "The email of the user is not valid!")
    private String email;

    @NotBlank(message = "The password is not valid!")
    @Size(min = 8, message = "The password length should be at least 8")
    private String password;
}
