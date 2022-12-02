package com.samdoree.fieldgeolog.AcceptanceTest;

import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.samdoree.fieldgeolog.ApiDocumentUtils.getDocumentRequest;
import static com.samdoree.fieldgeolog.ApiDocumentUtils.getDocumentResponse;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
class SpotTest {

    @LocalServerPort
    private int port;

    private RequestSpecification basicRequest;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        basicRequest = given()
                .baseUri("http://localhost")
                .port(port)
                .filter(documentationConfiguration(restDocumentation));
    }

    @Test
    @DisplayName("Spot 추가")
    void addSpot() {
        Map<String, Object> requestBody = new HashMap<>() {{
            put("latitude", 200.0);
            put("longitude", 200.0);
        }};
        LocalDateTime timeBeforeRequest = LocalDateTime.now();
        Spot spot = given()
                .spec(basicRequest).basePath("/api/spots")
                .accept("application/json")
                .contentType(ContentType.JSON).body(requestBody)
                .filter(document("add-spot",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("createDT").type(JsonFieldType.STRING).description("기록된 날짜")
                        )
                )).when().post()
                .then().statusCode(200)
                .extract().response().body().as(Spot.class);
        LocalDateTime timeAfterRequest = LocalDateTime.now();

        assertThat(spot.getLatitude()).isEqualTo(requestBody.get("latitude"));
        assertThat(spot.getLongitude()).isEqualTo(requestBody.get("longitude"));
        assertThat(timeBeforeRequest.compareTo(spot.getCreateDT()) < 0 && spot.getCreateDT().compareTo(timeAfterRequest) < 0).isTrue();
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