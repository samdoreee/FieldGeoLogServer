package com.samdoree.fieldgeolog.AcceptanceTest;

import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpotTest {

    @LocalServerPort
    private int port;

    private RequestSpecification basicRequest;

    @BeforeEach
    void setUp() {
        basicRequest = given()
                .baseUri("http://localhost")
                .port(port);
    }

    @Test
    @DisplayName("Spot 추가")
    void addSpot() {
        Map<String, Object> requestBody = new HashMap<>(){{
            put("latitude", 200.0);
            put("longitude", 200.0);
        }};
        LocalDateTime timeBeforeRequest = LocalDateTime.now();
        ResponseBody body = given()
                .spec(basicRequest).basePath("/api/spots")
                .contentType(ContentType.JSON).body(requestBody)
                .when().post()
                .then().statusCode(200)
                .extract().response().body();
        LocalDateTime timeAfterRequest = LocalDateTime.now();

        assertThat(body.jsonPath().getDouble("latitude")).isEqualTo(requestBody.get("latitude"));
        assertThat(body.jsonPath().getDouble("longitude")).isEqualTo(requestBody.get("longitude"));
        LocalDateTime createDt = LocalDateTime.parse(body.jsonPath().getString("createDt"));
        assertThat(timeBeforeRequest.compareTo(createDt) < 0 && createDt.compareTo(timeAfterRequest) < 0).isTrue();
    }

    @Test
    @DisplayName("Spot 목록 불러오기")
    void getAllSpotList() {

    }

    @Test
    @DisplayName("Spot 삭제하기")
    void deleteSpot() {
    }
}