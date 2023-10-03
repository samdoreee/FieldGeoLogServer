package com.samdoree.fieldgeolog.User.DTO;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class UserRequestDTO {

	@NotBlank
	private String id;
}
