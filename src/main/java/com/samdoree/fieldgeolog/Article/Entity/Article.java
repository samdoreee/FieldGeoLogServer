package com.samdoree.fieldgeolog.Article.Entity;

import com.samdoree.fieldgeolog.Article.DTO.ArticleRequestDTO;
import com.samdoree.fieldgeolog.Comment.Entity.Comment;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.User.Entity.User;

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

    // PersonalRecord에 등록된 썸네일 Path를 저장하기 위한 필드
    private String thumbnailPath;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String nickname;

    @CreatedDate
    private LocalDateTime createDT;

    private Boolean isValid;

    public static Article createFrom(ArticleRequestDTO articleRequestDTO, PersonalRecord personalRecord) {
        return new Article(articleRequestDTO, personalRecord);
    }

    public Article(ArticleRequestDTO articleRequestDTO, PersonalRecord personalRecord) {
        this.personalRecord = personalRecord;
        this.title = personalRecord.getRecordTitle();
        this.user = personalRecord.getUser();
        this.nickname = personalRecord.getNickname();
        this.createDT = LocalDateTime.now();
        this.isValid = true;

        // PersonalRecord에 등록된 썸네일 Path를 가져와서 저장
        this.thumbnailPath = personalRecord.getThumbnailPath();
    }

    //== 유효성 필드 메서드 ==//
    public void markAsInvalid() {
        this.isValid = false;
    }

    public boolean isValid() {
        return isValid;
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