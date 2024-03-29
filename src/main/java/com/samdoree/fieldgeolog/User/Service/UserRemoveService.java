package com.samdoree.fieldgeolog.User.Service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.samdoree.fieldgeolog.User.Entity.User;
import com.samdoree.fieldgeolog.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRemoveService {

	private final UserRepository userRepository;
	@Transactional
	public boolean removeUser(Long userId) throws Exception {
		Optional<User> optionalUser = userRepository.findById(userId);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.markAsInvalid();
			userRepository.save(user);
			return true;
		} else {
			return false;
		}

	}
}
