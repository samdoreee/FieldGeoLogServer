package com.samdoree.fieldgeolog.Comment.DTO;

import com.samdoree.fieldgeolog.Comment.Entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDTO {

    private Long id;
    private String nickName;
    private String content;
    private LocalDateTime createDT;
    private LocalDateTime modifyDT;

    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.nickName = comment.getNickName();
        this.content = comment.getContent();
        this.createDT = comment.getCreateDT();
        this.modifyDT = comment.getModifyDT();
    }

    public static CommentResponseDTO from(Comment comment) {
        return new CommentResponseDTO(comment);
    }
}
