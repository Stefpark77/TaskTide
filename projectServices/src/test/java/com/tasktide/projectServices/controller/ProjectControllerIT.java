package com.tasktide.projectServices.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tasktide.projectServices.security.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.util.UriComponentsBuilder;

import static io.restassured.RestAssured.given;
import java.util.Date;

@WebAppConfiguration
public class ProjectControllerIT {

    private String serviceLocation = "http://localhost:9400/projectServices";

    private String token;

    @BeforeEach
    public void setUp() {
        token = JWT.create()
                .withSubject("testUser")
                .withClaim("username", "testUser")
                .withClaim("role", Role.USER.name())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_00)) // 1 day
                .sign(Algorithm.HMAC256("test_secret_key"));
    }

    @Test
    public void getProjectByProjectId_OK() {
        UriComponentsBuilder noteServicesUrl = UriComponentsBuilder.fromHttpUrl(serviceLocation);
        String getProjectByProjectIdUri = noteServicesUrl.path("/project/{projectId}").buildAndExpand("1").toString();

        given()
            .contentType("application/json")
            .headers("Authorization", "Bearer " + token)
        .when()
            .get(getProjectByProjectIdUri)
        .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getProjectByProjectId_500() {
        UriComponentsBuilder noteServicesUrl = UriComponentsBuilder.fromHttpUrl(serviceLocation);
        String getProjectByProjectIdUri = noteServicesUrl.path("/praject/{projectId}").buildAndExpand("1").toString();

        given()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when()
                .get(getProjectByProjectIdUri)
                .then()
                .assertThat()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}