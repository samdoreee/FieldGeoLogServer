package com.samdoree.fieldgeolog.User.DTO;

import javax.validation.constraints.NotBlank;

import com.samdoree.fieldgeolog.Picture.Entity.Picture;

import lombok.Getter;

@Getter
public class UserRequestDTO {

	@NotBlank
	private Long id;

	private String email;

	private String nickName;

	private Picture profileImage;

}
