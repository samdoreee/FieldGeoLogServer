package com.samdoree.fieldgeolog.User.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordRequestDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Thumbnail.Entity.Thumbnail;
import com.samdoree.fieldgeolog.User.DTO.UserRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@Column(name = "user_id")
	private Long id;

	private String email;

	private String nickName;

	@OneToOne(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
	private Picture profileImage;

	private Boolean isValid;

	public static User createFrom(UserRequestDTO userRequestDTO){
		return new User(userRequestDTO);
	}

	private User(UserRequestDTO userRequestDTO){
		this.id = userRequestDTO.getId();
		this.email = userRequestDTO.getEmail();
		this.nickName = userRequestDTO.getNickName();
		this.profileImage = userRequestDTO.getProfileImage();
		this.isValid = true;
	}


	// 유효성 필드 메서드
	public void markAsInvalid() {
		this.isValid = false;
	}

	public boolean isValid() {
		return isValid;
	}
}