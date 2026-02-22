package com.folcolf.skool.server.controller;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

@QuarkusTest
class SubjectControllerTest {
    @Test
    void getAll_shouldReturn200ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .when()
                .get("/subjects")
                .then()
                .statusCode(200);
    }

    @Test
    void getAll_shouldReturn200ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .accept(ContentType.JSON)
                .when()
                .get("/subjects")
                .then()
                .statusCode(200);
    }

    @Test
    void getById_shouldReturn200ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .when()
                .get("/subjects/1")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    void getById_shouldReturn200Or204ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .accept(ContentType.JSON)
                .when()
                .get("/subjects/1")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    void createBatch_shouldReturn201ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body("[]")
                .when()
                .post("/subjects/batch")
                .then()
                .statusCode(anyOf(is(200), is(201), is(204)));
    }

    @Test
    void createBatch_shouldReturn403ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .contentType(ContentType.JSON)
                .body("[]")
                .when()
                .post("/subjects/batch")
                .then()
                .statusCode(403);
    }
}
