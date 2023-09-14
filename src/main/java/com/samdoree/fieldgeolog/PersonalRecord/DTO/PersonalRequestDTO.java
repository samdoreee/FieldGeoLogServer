package com.samdoree.fieldgeolog.PersonalRecord.DTO;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PersonalRequestDTO {
    @NotBlank
    private String recordTitle;

}
