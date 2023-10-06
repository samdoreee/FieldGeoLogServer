package com.samdoree.fieldgeolog.User.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Comment.Entity.Comment;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
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

	@Column(name = "profile_image")
	private String profileImage;

	private Boolean isValid;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<PersonalRecord> personalRecordList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Article> articleList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Comment> commentList = new ArrayList<>();

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