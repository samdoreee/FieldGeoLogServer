package com.samdoree.fieldgeolog.Article.Entity;

import com.samdoree.fieldgeolog.Article.DTO.ArticleRequestDTO;
import com.samdoree.fieldgeolog.Comment.Entity.Comment;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> commentList = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createDT;

    public static Article createFrom(ArticleRequestDTO articleRequestDTO, PersonalRecord personalRecord) {
        return new Article(articleRequestDTO, personalRecord);
    }

    public Article(ArticleRequestDTO articleRequestDTO, PersonalRecord personalRecord) {
        this.personalRecord = personalRecord;
        this.createDT = LocalDateTime.now();
    }

    //== 연관관계 메서드 ==//
    public void addComment(Comment comment) {
        commentList.add(comment);
        comment.belongToArticle(this);
    }

    public void removeComment() {
        commentList.clear();
    }


}
