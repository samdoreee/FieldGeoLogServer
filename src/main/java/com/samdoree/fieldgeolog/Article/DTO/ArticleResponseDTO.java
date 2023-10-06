package com.samdoree.fieldgeolog.Article.DTO;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleResponseDTO {

    private Long id;
    private PersonalRecordResponseDTO personalRecordResponseDTO;
    private String title;
    private Long userId;
    private String nickname;
    private LocalDateTime createDT;

    public ArticleResponseDTO(Article article, PersonalRecordResponseDTO personalRecordResponseDTO) {
        this.id = article.getId();
        this.personalRecordResponseDTO = personalRecordResponseDTO;
        this.title = personalRecordResponseDTO.getRecordTitle();
        this.userId = personalRecordResponseDTO.getUserId();
        this.nickname = personalRecordResponseDTO.getNickname();
        this.createDT = article.getCreateDT();
    }

    public static ArticleResponseDTO fromPersonalRecord(Article article, PersonalRecordResponseDTO personalRecordResponseDTO) {
        return new ArticleResponseDTO(article, personalRecordResponseDTO);
    }
}