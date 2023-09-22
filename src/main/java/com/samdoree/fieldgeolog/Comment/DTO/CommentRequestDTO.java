package com.samdoree.fieldgeolog.Comment.DTO;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentRequestDTO {

    @NotBlank
    private String nickName;

    @NotBlank
    private String content;
}
