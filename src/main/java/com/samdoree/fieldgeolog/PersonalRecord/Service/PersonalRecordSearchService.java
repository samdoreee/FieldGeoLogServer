package com.samdoree.fieldgeolog.PersonalRecord.Service;

import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonalRecordSearchService {

    private final PersonalRecordRepository personalRecordRepository;

    public List<PersonalRecordResponseDTO> getAllPersonalRecordList() {

        List<PersonalRecord> personalRecordList = personalRecordRepository.findAll();
        return personalRecordList.stream().map(PersonalRecordResponseDTO::new).collect(Collectors.toList());
    }

    public PersonalRecordResponseDTO getOnePersonalRecord(Long personalRecordId) {

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());

        return PersonalRecordResponseDTO.from(personalRecord);
    }
}
