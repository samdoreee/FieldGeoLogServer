package com.samdoree.fieldgeolog.PersonalRecord.Service;

import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRequestDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonalRecordModifyService {

    private final PersonalRecordRepository personalRecordRepository;

    @Transactional
    public PersonalRecordResponseDTO modifyPersonalRecord(Long personalRecordId, PersonalRequestDTO personalRequestDTO) throws Exception {

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());

        personalRecord.modifyPersonalRecord(personalRequestDTO);
        return PersonalRecordResponseDTO.from(personalRecord);
    }
}
