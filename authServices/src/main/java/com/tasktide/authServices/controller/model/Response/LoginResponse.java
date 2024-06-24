package com.tasktide.authServices.controller.model.Response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private String token;
    private String userId;
}
