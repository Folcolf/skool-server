package com.folcolf.skool.server.controller;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

@QuarkusTest
class StudentControllerTest {
    @Test
    void getAll_shouldReturn200ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .when()
                .get("/students")
                .then()
                .statusCode(200);
    }

    @Test
    void getAll_shouldReturn403ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .accept(ContentType.JSON)
                .when()
                .get("/students")
                .then()
                .statusCode(403);
    }

    @Test
    void getById_shouldReturn200Or204ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .when()
                .get("/students/1")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    void getById_shouldReturn403ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .accept(ContentType.JSON)
                .when()
                .get("/students/1")
                .then()
                .statusCode(403);
    }

    @Test
    void create_shouldReturn201ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/students")
                .then()
                .statusCode(anyOf(is(200), is(201), is(204)));
    }

    @Test
    void create_shouldReturn403ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/students")
                .then()
                .statusCode(403);
    }

    @Test
    void delete_shouldReturn204ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .when()
                .delete("/students/1")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    void delete_shouldReturn403ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .when()
                .delete("/students/1")
                .then()
                .statusCode(403);
    }
}
