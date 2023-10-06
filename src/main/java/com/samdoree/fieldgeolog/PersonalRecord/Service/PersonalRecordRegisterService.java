package com.samdoree.fieldgeolog.PersonalRecord.Service;

import java.util.Optional;

import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordRequestDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Thumbnail.Entity.Thumbnail;
import com.samdoree.fieldgeolog.Thumbnail.Service.ThumbnailRegisterService;
import com.samdoree.fieldgeolog.User.DTO.UserResponseDTO;
import com.samdoree.fieldgeolog.User.Entity.User;
import com.samdoree.fieldgeolog.User.Repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonalRecordRegisterService {

    private final PersonalRecordRepository personalRecordRepository;
    private final ThumbnailRegisterService thumbnailRegisterService;
    private final UserRepository userRepository;

    @Transactional
    public PersonalRecordResponseDTO addPersonalRecord(PersonalRecordRequestDTO personalRecordRequestDTO) throws Exception {

        PersonalRecord personalRecord = PersonalRecord.createFrom(personalRecordRequestDTO);

        // PersonalRecord에 대한 Thumbnail 생성 및 연결
        Thumbnail thumbnail = thumbnailRegisterService.addPersonalRecordThumbnail(personalRecord, null);
        personalRecord.setFileName(thumbnail.getFileName());

        // User 정보 입력
        Optional<User> optionalUser = userRepository.findById(personalRecordRequestDTO.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            personalRecord.addUserInfo(user);
        } else {
            personalRecord.addUserInfo(new User());
        }

        // PersonalRecord 저장
        personalRecordRepository.save(personalRecord);
        return PersonalRecordResponseDTO.from(personalRecord);
    }
}
