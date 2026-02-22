package com.folcolf.skool.server.controller;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

@QuarkusTest
class GradeControllerTest {
    @Test
    void getAll_shouldReturn200ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .when()
                .get("/grades")
                .then()
                .statusCode(200);
    }

    @Test
    void getAll_shouldReturn403ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .accept(ContentType.JSON)
                .when()
                .get("/grades")
                .then()
                .statusCode(403);
    }

    @Test
    void getById_shouldReturn200Or204ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .when()
                .get("/grades/1")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    void getById_shouldReturn403ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .accept(ContentType.JSON)
                .when()
                .get("/grades/1")
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
                .post("/grades")
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
                .post("/grades")
                .then()
                .statusCode(403);
    }

    @Test
    void delete_shouldReturn204ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .when()
                .delete("/grades/1")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    void delete_shouldReturn403ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .when()
                .delete("/grades/1")
                .then()
                .statusCode(403);
    }

    @Test
    void createBatch_shouldReturn201ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body("[]")
                .when()
                .post("/grades/batch")
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
                .post("/grades/batch")
                .then()
                .statusCode(403);
    }

    @Test
    void getGrades_shouldReturn200ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .when()
                .get("/grades/student/1")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    void getGrades_shouldReturn403ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .accept(ContentType.JSON)
                .when()
                .get("/grades/student/1")
                .then()
                .statusCode(403);
    }

    @Test
    void getAverages_shouldReturn200ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .when()
                .get("/grades/student/1/averages")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    void getAverages_shouldReturn403ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .accept(ContentType.JSON)
                .when()
                .get("/grades/student/1/averages")
                .then()
                .statusCode(403);
    }

    @Test
    void getClassAverage_shouldReturn200ForAdmin() {
        given()
                .auth().basic("admin", "admin")
                .accept(ContentType.JSON)
                .when()
                .get("/grades/class/1/average")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    void getClassAverage_shouldReturn403ForNonAdmin() {
        given()
                .auth().basic("user", "user")
                .accept(ContentType.JSON)
                .when()
                .get("/grades/class/1/average")
                .then()
                .statusCode(403);
    }
}
