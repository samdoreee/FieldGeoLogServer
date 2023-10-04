package com.samdoree.fieldgeolog.User.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.samdoree.fieldgeolog.User.DTO.UserRequestDTO;
import com.samdoree.fieldgeolog.User.DTO.UserResponseDTO;
import com.samdoree.fieldgeolog.User.Entity.User;
import com.samdoree.fieldgeolog.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRegisterService {

	private final UserRepository userRepository;

	@Transactional
	public UserResponseDTO addUser(UserRequestDTO userRequestDTO) throws Exception{
		User user = User.createFrom(userRequestDTO);

		userRepository.save(user);
		return UserResponseDTO.from(user);
	}
}
