package com.samdoree.fieldgeolog.PersonalRecord.Service;

import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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

    public List<PersonalRecordResponseDTO> sortAllPersonalRecordOrderByASC() {

        List<PersonalRecord> personalRecordList = personalRecordRepository.findAll(Sort.by(Sort.Direction.ASC, "createDT"));
        return personalRecordList.stream().map(PersonalRecordResponseDTO::new).collect(Collectors.toList());
    }

    public List<PersonalRecordResponseDTO> sortAllPersonalRecordOrderByDESC() {

        List<PersonalRecord> personalRecordList = personalRecordRepository.findAll(Sort.by(Sort.Direction.DESC, "createDT"));
        return personalRecordList.stream().map(PersonalRecordResponseDTO::new).collect(Collectors.toList());
    }

    // 제목 기반 검색
    public List<PersonalRecordResponseDTO> searchByTitle(String keyword) {

        List<PersonalRecord> personalRecordList = personalRecordRepository.findByRecordTitleContaining(keyword);
        return personalRecordList.stream().map(PersonalRecordResponseDTO::new).collect(Collectors.toList());
    }

//    // 닉네임 기반 검색
//    public List<PersonalRecordResponseDTO> searchByNickname(String keyword) {
//
//        List<PersonalRecord> personalRecordList = personalRecordRepository.findByNicknameContaining(keyword);
//        return personalRecordList.stream().map(PersonalRecordResponseDTO::new).collect(Collectors.toList());
//    }

    // 검색 유형이 잘못된 경우 빈 목록을 반환하는 메서드
    public List<PersonalRecordResponseDTO> emptySearchResult(){
        // 빈 목록을 반환한다.
        return Collections.emptyList();
    }

    public PersonalRecordResponseDTO getOnePersonalRecord(Long personalRecordId) {

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());

        return PersonalRecordResponseDTO.from(personalRecord);
    }
}
