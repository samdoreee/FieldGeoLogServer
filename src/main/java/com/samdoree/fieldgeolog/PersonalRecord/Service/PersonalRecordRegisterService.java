package com.samdoree.fieldgeolog.PersonalRecord.Service;

import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordRequestDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonalRecordRegisterService {

    private final PersonalRecordRepository personalRecordRepository;

    @Transactional
    public PersonalRecordResponseDTO addPersonalRecord(PersonalRecordRequestDTO personalRecordRequestDTO) throws Exception {

        PersonalRecord personalRecord = personalRecordRepository.save(PersonalRecord.createFrom(personalRecordRequestDTO));
        return PersonalRecordResponseDTO.from(personalRecord);
    }
}
