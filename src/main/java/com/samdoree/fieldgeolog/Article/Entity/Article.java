package com.samdoree.fieldgeolog.Article.Entity;

import com.samdoree.fieldgeolog.Article.DTO.ArticleRequestDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personalRecord_id")
    private PersonalRecord personalRecord;

    @CreatedDate
    private LocalDateTime createDT;


    public static Article createFrom(ArticleRequestDTO articleRequestDTO, PersonalRecord personalRecord) {
        return new Article(articleRequestDTO, personalRecord);
    }

    public Article(ArticleRequestDTO articleRequestDTO, PersonalRecord personalRecord) {
        this.personalRecord = personalRecord;
        this.createDT = LocalDateTime.now();
    }

}
