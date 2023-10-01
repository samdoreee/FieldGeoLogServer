package com.samdoree.fieldgeolog.PersonalRecord.Service;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordRequestDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonalRecordModifyService {

    private final PersonalRecordRepository personalRecordRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public PersonalRecordResponseDTO modifyPersonalRecord(Long personalRecordId, PersonalRecordRequestDTO personalRecordRequestDTO) throws Exception {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));

        // 해당 PersonalRecord와 1:1 관계를 맺고 있는 Article isValid = false로 설정
        if(articleRepository.existsByPersonalRecordId(personalRecordId)){
            Article article = articleRepository.findByPersonalRecordId(personalRecordId);
            article.markAsInvalid();
            articleRepository.save(article);
        }

        validPersonalRecord.modifyPersonalRecord(personalRecordRequestDTO);
        return PersonalRecordResponseDTO.from(validPersonalRecord);
    }
}
