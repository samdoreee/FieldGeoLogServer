package com.samdoree.fieldgeolog.Memo.DTO;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemoRequestDTO {

    @NotBlank
    private String description;
}
