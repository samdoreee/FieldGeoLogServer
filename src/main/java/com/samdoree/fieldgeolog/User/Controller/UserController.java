package com.samdoree.fieldgeolog.User.Controller;

import com.samdoree.fieldgeolog.User.DTO.UserRequestDTO;
import com.samdoree.fieldgeolog.User.DTO.UserResponseDTO;
import com.samdoree.fieldgeolog.User.Service.UserModifyService;
import com.samdoree.fieldgeolog.User.Service.UserRegisterService;
import com.samdoree.fieldgeolog.User.Service.UserRemoveService;
import com.samdoree.fieldgeolog.User.Service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserRegisterService userRegisterService;
	private final UserModifyService userModifyService;
	private final UserSearchService userSearchService;
	private final UserRemoveService userRemoveService;

	@PostMapping()
	public UserResponseDTO addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) throws Exception {
		return userRegisterService.addUser(userRequestDTO);
	}

	@GetMapping()
	public List<UserResponseDTO> getAllUserList(){
		return userSearchService.getAllUser();
	}

	@PatchMapping("/{userId}")
	public boolean modifyUser(@PathVariable Long userId,
		@RequestParam(required = false) String nickname,
		@RequestParam(required = false) String profileimage) throws Exception {

		boolean nicknameUpdated = true;
		boolean profileImageUpdated = true;

		if (nickname != null) {
			nicknameUpdated = userModifyService.modifyUserNickname(userId, nickname);
		}

		if (profileimage != null) {
			profileImageUpdated = userModifyService.modifyUserProfileImage(userId, profileimage);
		}

		if (nickname == null && profileimage == null) {
			throw new IllegalArgumentException("Either nickname or profileimage or both should be provided.");
		}

		return nicknameUpdated && profileImageUpdated;
	}

	// @GetMapping("/{userId}")
	// public UserResponseDTO getOneUser(@PathVariable Long userId) throws Exception{
	// 	return userSearchService.getOneUser(userId);
	// }
	// @PatchMapping("/{userId}/profile-image")
	// public boolean modifyUserProfileImage(@PathVariable Long userId, @RequestParam String profileimage) throws Exception{
	// 	return userModifyService.modifyUserProfileImage(userId, profileimage);
	// }

	@DeleteMapping("/{userId}")
	public boolean removeUser(@PathVariable Long userId) throws Exception{
		return userRemoveService.removeUser(userId);
	}

}
