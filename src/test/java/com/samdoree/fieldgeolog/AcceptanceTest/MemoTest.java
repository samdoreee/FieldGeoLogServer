package com.samdoree.fieldgeolog.AcceptanceTest;

import com.samdoree.fieldgeolog.File.Entity.File;
import com.samdoree.fieldgeolog.File.Repository.FileRepository;
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
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.samdoree.fieldgeolog.ApiDocumentUtils.getDocumentRequest;
import static com.samdoree.fieldgeolog.ApiDocumentUtils.getDocumentResponse;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemoTest {

    @LocalServerPort
    private int port;

    private RequestSpecification basicRequest;

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private MemoRepository memoRepository;

    @Autowired
    private FileRepository fileRepository;

    private final List<Spot> preGeneratedSpots = new ArrayList<>();
    private final List<Memo> preGeneratedMemos = new ArrayList<>();
    private final List<File> preGeneratedFiles = new ArrayList<>();

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        basicRequest = given()
                .baseUri("http://localhost")
                .port(port)
                .filter(documentationConfiguration(restDocumentation));

        for (int i = 0; i < 2; i++) {
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
        for (int i = 0; i < 4; i++) {
            preGeneratedMemos.add(memoRepository.save(Memo.builder()
                    .spot(preGeneratedSpots.get(0))
                    .description("description " + i)
                    .build()));
        }
        memoRepository.save(Memo.builder()
                .spot(preGeneratedSpots.get(1))
                .description("description ")
                .build());
        for (int i = 0; i < 2; i++) {
            preGeneratedFiles.add(fileRepository.save(File.builder()
                    .fileName("test")
                    .build())
            );
        }

    }

    @Test
    @DisplayName("memo ??????")
    void addMemo() {
        int numOfMemoBeforeAddRequest = memoRepository.findAll().size();
        MemoResponseDTO memo = given()
                .spec(basicRequest).basePath("/api/spots")
                .pathParams("spotId", preGeneratedSpots.get(0).getId())
                .accept("application/json")
                .multiPart("memo", "{ \"description\": \"test desc\" }", "application/json")
                .multiPart("file", "image.png", new byte[10])
                .contentType("multipart/form-data")
                .filter(document("add-memo",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("spotId").description("spot id")
                        ),
                        requestParts(
                                partWithName("memo").description("description"),
                                partWithName("file").description("?????????")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("fileList").type(JsonFieldType.ARRAY).description("????????? ?????????"),
                                fieldWithPath("fileList[].id").type(JsonFieldType.NUMBER).description("?????? id"),
                                fieldWithPath("fileList[].fileName").type(JsonFieldType.STRING).description("?????? fileName"),
                                fieldWithPath("fileList[].filePath").type(JsonFieldType.STRING).description("?????? filePath")
                        )
                )).when().post("/{spotId}/memos")
                .then().log().all().statusCode(200)
                .extract().response().body().as(MemoResponseDTO.class);
        assertThat(memo.getDescription()).isEqualTo("test desc");
        assertThat(memo.getFileList().size()).isEqualTo(1);
        assertThat(memoRepository.findAll().size()).isEqualTo(numOfMemoBeforeAddRequest + 1);
    }

    @Test
    @DisplayName("?????? Spot??? ?????? memo ????????????")
    void getAllMemoList() {
        given()
                .spec(basicRequest).basePath("/api/spots")
                .pathParams("spotId", preGeneratedSpots.get(0).getId())
                .accept("application/json")
                .multiPart("memo", "{ \"description\": \"test desc\" }", "application/json")
                .multiPart("file", "image.png", new byte[10])
                .contentType("multipart/form-data").when().post("/{spotId}/memos")
                .then().log().all().statusCode(200);
        MemoResponseDTO[] memos = given()
                .spec(basicRequest).basePath("/api/spots")
                .pathParams("spotId", preGeneratedSpots.get(0).getId())
                .accept("application/json")
                .filter(document("get-all-memo",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("spotId").description("spot id")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("[].description").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("[].fileList").type(JsonFieldType.ARRAY).description("????????? ?????????"),
                                fieldWithPath("[].fileList[].id").type(JsonFieldType.NUMBER).description("?????? id"),
                                fieldWithPath("[].fileList[].fileName").type(JsonFieldType.STRING).description("?????? fileName"),
                                fieldWithPath("[].fileList[].filePath").type(JsonFieldType.STRING).description("?????? filePath")
                        )
                )).when().get("/{spotId}/memos")
                .then().log().all().statusCode(200)
                .extract().response().body().as(MemoResponseDTO[].class);
        assertThat(memos.length).isEqualTo(preGeneratedMemos.size() + 1);
    }
}