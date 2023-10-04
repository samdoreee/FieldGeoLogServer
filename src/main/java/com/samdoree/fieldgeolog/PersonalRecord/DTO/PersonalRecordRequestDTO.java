package com.samdoree.fieldgeolog.PersonalRecord.DTO;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

import com.samdoree.fieldgeolog.User.Entity.User;

@Getter
public class PersonalRecordRequestDTO {

    @NotBlank
    private String recordTitle;

    private Long userId;



}
