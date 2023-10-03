package com.samdoree.fieldgeolog.User.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordRequestDTO;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Thumbnail.Entity.Thumbnail;
import com.samdoree.fieldgeolog.Thumbnail.Service.ThumbnailRegisterService;
import com.samdoree.fieldgeolog.User.DTO.UserRequestDTO;
import com.samdoree.fieldgeolog.User.DTO.UserResponseDTO;
import com.samdoree.fieldgeolog.User.Entity.User;
import com.samdoree.fieldgeolog.User.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRegisterService {

	private final PersonalRecordRepository personalRecordRepository;
	private final ThumbnailRegisterService thumbnailRegisterService;

	private final UserRepository userRepository;

	@Transactional
	public PersonalRecordResponseDTO addPersonalRecord(PersonalRecordRequestDTO personalRecordRequestDTO) throws Exception {

		PersonalRecord personalRecord = PersonalRecord.createFrom(personalRecordRequestDTO);

		// PersonalRecord에 대한 Thumbnail 생성 및 연결
		Thumbnail thumbnail = thumbnailRegisterService.addPersonalRecordThumbnail(personalRecord, null);
		personalRecord.setThumbnailPath(thumbnail.getFilePath());

		// PersonalRecord 저장
		personalRecordRepository.save(personalRecord);
		return PersonalRecordResponseDTO.from(personalRecord);
	}

	@Transactional
	public UserResponseDTO addUser(UserRequestDTO userRequestDTO) throws Exception{
		User user = User.createFrom(userRequestDTO);


		// PersonalRecord 저장
		userRepository.save(user);
		return UserResponseDTO.from(user);

	}
}
