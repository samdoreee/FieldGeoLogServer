package com.samdoree.fieldgeolog.User.Controller;

import com.samdoree.fieldgeolog.User.DTO.UserRequestDTO;
import com.samdoree.fieldgeolog.User.DTO.UserResponseDTO;
import com.samdoree.fieldgeolog.User.Service.UserModifyService;
import com.samdoree.fieldgeolog.User.Service.UserRegisterService;
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

	@PostMapping("/add")
	public UserResponseDTO addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) throws Exception {
		return userRegisterService.addUser(userRequestDTO);
	}

	@GetMapping("/all")
	public List<UserResponseDTO> getAllUserList(){
		return userSearchService.getAllUser();
	}

	@GetMapping("/{userId}")
	public UserResponseDTO getOneUser(@PathVariable Long userId) throws Exception{
		return userSearchService.getOneUser(userId);
	}

	@GetMapping("/{userId}/update")
	public boolean modifyUserNickname(@PathVariable Long userId, @RequestParam String nickName) throws Exception{
		return userModifyService.modifyUserNickname(userId, nickName);
	}

	@GetMapping("/{userId}/update")
	public boolean modifyUserProfileImage(@PathVariable Long userId, @RequestParam String profileImage) throws Exception{
		return userModifyService.modifyUserProfileImage(userId, profileImage);
	}

	@GetMapping("/{userId}/remove")
	public boolean removeUser(@PathVariable Long userId) throws Exception{
		return userModifyService.removeUser(userId);
	}

}
