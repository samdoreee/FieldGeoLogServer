package com.samdoree.fieldgeolog.User.DTO;

import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.User.Entity.User;

public class UserResponseDTO {

	private Long id;

	private String email;

	private String nickName;

	private String profileImage;

	private Boolean isValid;

	public UserResponseDTO(User user){
		this.id = user.getId();
		this.email = user.getEmail();
		this.nickName = user.getNickName();
		this.profileImage = user.getProfileImage();
		this.isValid = user.getIsValid();
	}

	public static UserResponseDTO from(User user) {
		return new UserResponseDTO(user);
	}
}
