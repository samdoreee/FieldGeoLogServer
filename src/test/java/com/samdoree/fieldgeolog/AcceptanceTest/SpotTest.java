package com.samdoree.fieldgeolog.AcceptanceTest;

import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.samdoree.fieldgeolog.ApiDocumentUtils.getDocumentRequest;
import static com.samdoree.fieldgeolog.ApiDocumentUtils.getDocumentResponse;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SpotTest {

    @LocalServerPort
    private int port;

    private RequestSpecification basicRequest;

    @Autowired
    private SpotRepository spotRepository;

    private final List<Spot> preGeneratedSpots = new ArrayList<>();

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        basicRequest = given()
                .baseUri("http://localhost")
                .port(port)
                .filter(documentationConfiguration(restDocumentation));

        for (int i = 0; i < 4; i++) {
            preGeneratedSpots.add(spotRepository.save(Spot.builder()
                    .latitude(100.0 + i)
                    .longitude(100.0)
                    .createDT(LocalDateTime.now())
                    .weatherInfo("????????????: ?????? / ????????????: ?????? ??????")
                    .strike(56)
                    .rockType("?????????")
                    .geoStructure("??????")
                    .dip(20)
                    .direction("NS")
                    .build()));
        }
    }

    @Test
    @DisplayName("Spot ??????")
    void addSpot() {
        Map<String, Object> requestBody = new HashMap<>() {{
            put("latitude", 36.6287);
            put("longitude", 127.4606);
            put("strike", 56);
            put("rockType", "?????????");
            put("geoStructure", "??????");
            put("dip", 20);
            put("direction", "NS");
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
                                fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("strike").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("geoStructure").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("rockType").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("dip").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("direction").type(JsonFieldType.STRING).description("??????")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("createDT").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("weatherInfo").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("strike").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("geoStructure").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("rockType").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("dip").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("direction").type(JsonFieldType.STRING).description("??????")
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
    @DisplayName("Spot ?????? ????????????")
    void getAllSpotList() {
        Spot[] spots = given()
                .spec(basicRequest).basePath("/api/spots")
                .accept("application/json")
                .contentType(ContentType.JSON)
                .filter(document("read-all-spot",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("[].latitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("[].longitude").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("[].createDT").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("[].weatherInfo").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("[].strike").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("[].geoStructure").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("[].rockType").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("[].dip").type(JsonFieldType.NUMBER).description("??????"),
                                fieldWithPath("[].direction").type(JsonFieldType.STRING).description("??????")
                        )
                )).when().get()
                .then().statusCode(200)
                .log().all()
                .extract().response().body().as(Spot[].class);
        assertThat(preGeneratedSpots).hasSameElementsAs(Arrays.stream(spots).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Spot ????????????")
    void deleteSpot() {
        Long deleteSpotId = preGeneratedSpots.get(0).getId();
        given()
                .spec(basicRequest).basePath("/api/spots")
                .pathParams("spotId", deleteSpotId)
                .accept("application/json")
                .contentType(ContentType.JSON)
                .filter(document("remove-spot",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("spotId").description("spot id")
                        )
                )).when().delete("/{spotId}")
                .then()
                .log().all()
                .body(equalTo("true"))
                .statusCode(200);
        assertThat(spotRepository.findAll()).hasSameElementsAs(preGeneratedSpots.subList(1, preGeneratedSpots.size()));
    }
}