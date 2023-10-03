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
import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Thumbnail.Entity.Thumbnail;

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

	/*


	사용자 정보 요청 성공 : User(id=3031357401,
	properties={
		nickname=김정현,
		profile_image=https://k.kakaocdn.net/dn/b8h0Gr/btslaJdqaWx/BsKD2iZfrPYN9emeyKDU8K/img_640x640.jpg,
		thumbnail_image=https://k.kakaocdn.net/dn/b8h0Gr/btslaJdqaWx/BsKD2iZfrPYN9emeyKDU8K/img_110x110.jpg
	},
	kakaoAccount=Account(profileNeedsAgreement=null,
		profileNicknameNeedsAgreement=false,
		profileImageNeedsAgreement=false,
		profile=Profile(nickname=김정현,
			profileImageUrl=https://k.kakaocdn.net/dn/b8h0Gr/btslaJdqaWx/BsKD2iZfrPYN9emeyKDU8K/img_640x640.jpg,
			thumbnailImageUrl=https://k.kakaocdn.net/dn/b8h0Gr/btslaJdqaWx/BsKD2iZfrPYN9emeyKDU8K/img_110x110.jpg,
		),
		email=6843wjdgus@naver.com,
		ageRange=AGE_20_29,
	connectedAt=Thu Sep 21 20:22:54 GMT+09:00 2023, synchedAt=null, hasSignedUp=null
	)

	 */
	@Id
	@Column(name = "user_id")
	private Long id;

	private String email;

	private String nickName;

	@OneToOne(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
	private Picture profileImage;

	private Boolean isValid;


	// 유효성 필드 메서드
	public void markAsInvalid() {
		this.isValid = false;
	}

	public boolean isValid() {
		return isValid;
	}
}