package com.samdoree.fieldgeolog.User.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.query.Param;

import com.samdoree.fieldgeolog.User.Entity.User;

@EnableJpaAuditing
public interface UserRepository extends JpaRepository<User, Long> {

	@Modifying
	@Query("UPDATE User u SET u.nickname = :nickname WHERE u.id = :id")
	void updateNickname(@Param("id") Long id, @Param("nickname") String nickname);

	@Modifying
	@Query("UPDATE User u SET u.profile_image = :profileImage WHERE u.id = :id")
	void updateProfileImage(@Param("id") Long id, @Param("profileImage") String profileImage);
}

