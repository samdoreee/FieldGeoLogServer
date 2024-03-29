package com.samdoree.fieldgeolog.Comment.Entity;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Comment.DTO.CommentRequestDTO;
import com.samdoree.fieldgeolog.User.Entity.User;

import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String nickName;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDT;
    @CreatedDate
    private LocalDateTime modifyDT;

    private Boolean isValid;

    public static Comment createFrom(Article article, User user, CommentRequestDTO commentRequestDTO) throws Exception {
        return new Comment(article, user, commentRequestDTO);
    }

    private Comment(Article article, User user, CommentRequestDTO commentRequestDTO) throws Exception {
        this.article = article;
        this.user = user;
        this.nickName = user.getNickName();
        this.content = commentRequestDTO.getContent();
        this.createDT = LocalDateTime.now();
        this.modifyDT = LocalDateTime.now();
        this.isValid = true;
    }

    public void modifyComment(CommentRequestDTO commentRequestDTO) {
        this.modifyDT = LocalDateTime.now();
        this.content = commentRequestDTO.getContent();
    }

    //== 유효성 필드 메서드 ==//
    public void markAsInvalid() {
        this.isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    //== 연관관계 메서드 ==//
    public void belongToArticle(Article article) {
        this.article = article;
    }
}