package com.samdoree.fieldgeolog.User.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.samdoree.fieldgeolog.User.DTO.UserResponseDTO;
import com.samdoree.fieldgeolog.User.Entity.User;
import com.samdoree.fieldgeolog.User.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSearchService {

	private final UserRepository userRepository;

	public List<UserResponseDTO> getAllUser(){
		List<User> userList = userRepository.findAll();
		return userList.stream()
			.map(UserResponseDTO::new)
			.collect(Collectors.toList());
	}

	public UserResponseDTO getOneUser(Long userId){
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return new UserResponseDTO(user);
		} else {
			return new UserResponseDTO();
		}
	}

}
