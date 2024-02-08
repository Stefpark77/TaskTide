package com.tasktide.authServices.controller.model.Response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    String token;
    String userId;
}
