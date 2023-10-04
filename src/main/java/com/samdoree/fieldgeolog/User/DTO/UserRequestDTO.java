package com.samdoree.fieldgeolog.User.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.samdoree.fieldgeolog.Picture.Entity.Picture;

import lombok.Getter;

@Getter
public class UserRequestDTO {

	@NotNull
	private Long id;

	private String email;

	private String nickName;

	private String profileImage;

}
