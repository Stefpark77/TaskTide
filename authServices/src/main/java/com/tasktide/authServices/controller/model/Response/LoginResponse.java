package com.tasktide.authServices.controller.model.Response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private String token;
    private String userId;
}
