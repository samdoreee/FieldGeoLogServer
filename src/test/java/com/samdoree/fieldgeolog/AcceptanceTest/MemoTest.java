package com.samdoree.fieldgeolog.AcceptanceTest;

import com.samdoree.fieldgeolog.Memo.DTO.MemoResponseDTO;
import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Memo.Repository.MemoRepository;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.samdoree.fieldgeolog.ApiDocumentUtils.getDocumentRequest;
import static com.samdoree.fieldgeolog.ApiDocumentUtils.getDocumentResponse;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
class MemoTest {

    @LocalServerPort
    private int port;

    private RequestSpecification basicRequest;

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private MemoRepository memoRepository;

    private final List<Spot> preGeneratedSpots = new ArrayList<>();
    private final List<Memo> preGeneratedMemos = new ArrayList<>();

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        basicRequest = given()
                .baseUri("http://localhost")
                .port(port)
                .filter(documentationConfiguration(restDocumentation));

        memoRepository.deleteAll();
        spotRepository.deleteAll();
        for (int i = 0; i < 2; i++) {
            preGeneratedSpots.add(spotRepository.save(Spot.builder()
                    .latitude(100.0 + i)
                    .longitude(100.0)
                    .createDT(LocalDateTime.now())
                    .weatherInfo("하늘상태: 흐림 / 강수형태: 강수 없음")
                    .strike(56)
                    .rockType("화성암")
                    .geoStructure("굴곡")
                    .dip(20)
                    .direction("NS")
                    .build()));
        }
        for (int i = 0; i < 4; i++) {
            preGeneratedMemos.add(memoRepository.save(Memo.builder()
                    .spot(preGeneratedSpots.get(0))
                    .description("description " + i)
                    .build()));
        }
        preGeneratedMemos.add(memoRepository.save(Memo.builder()
                .spot(preGeneratedSpots.get(1))
                .description("description ")
                .build()));
    }

    @Test
    @DisplayName("memo 추가")
    void addMemo() {
        int numOfMemoBeforeAddRequest = memoRepository.findAll().size();
        MemoResponseDTO memo = given()
                .spec(basicRequest).basePath("/api/spots")
                .pathParams("spotId", preGeneratedSpots.get(0).getId())
                .accept("application/json")
                .multiPart("memo", "{ \"description\": \"test desc\" }", "application/json")
                .contentType("multipart/form-data")
                .filter(document("add-memo",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("메모 내용"),
                                fieldWithPath("fileList").type(JsonFieldType.ARRAY).description("첨부된 사진들")
                        )
                )).when().post("/{spotId}/memos")
                .then().log().all().statusCode(200)
                .extract().response().body().as(MemoResponseDTO.class);
        assertThat(memo.getDescription()).isEqualTo("test desc");
        assertThat(memoRepository.findAll().size()).isEqualTo(numOfMemoBeforeAddRequest + 1);
    }

    @Test
    @DisplayName("특정 Spot의 모든 memo 가져오기")
    void getAllMemoList() {
        MemoResponseDTO[] memos = given()
                .spec(basicRequest).basePath("/api/spots")
                .pathParams("spotId", preGeneratedSpots.get(0).getId())
                .accept("application/json")
                .filter(document("add-memo",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("[].description").type(JsonFieldType.STRING).description("메모 내용"),
                                fieldWithPath("[].fileList").type(JsonFieldType.ARRAY).description("첨부된 사진들")
                        )
                )).when().get("/{spotId}/memos")
                .then().log().all().statusCode(200)
                .extract().response().body().as(MemoResponseDTO[].class);
        assertThat(memos).hasSameElementsAs(memoRepository.findAllBySpotId(preGeneratedSpots.get(0).getId()).stream().map(MemoResponseDTO::from).collect(Collectors.toList()));
    }
}