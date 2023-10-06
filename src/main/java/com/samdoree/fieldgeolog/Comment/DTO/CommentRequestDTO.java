package com.samdoree.fieldgeolog.Comment.DTO;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CommentRequestDTO {

    @NotNull
    private Long userId;

    @NotBlank
    private String nickName;

    @NotBlank
    private String content;
}
