package com.samdoree.fieldgeolog.Article.DTO;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ArticleRequestDTO {

    @NotNull
    private Long recordId;

}
