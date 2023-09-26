package com.samdoree.fieldgeolog.PersonalRecord.Service;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import com.samdoree.fieldgeolog.Article.Service.ArticleRemoveService;
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
    private final ArticleRemoveService articleRemoveService;
    private final ArticleRepository articleRepository;

    @Transactional
    public PersonalRecordResponseDTO modifyPersonalRecord(Long personalRecordId, PersonalRecordRequestDTO personalRecordRequestDTO) throws Exception {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.getIsValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));

        // 해당 PersonalRecord와 1:1 관계를 맺고 있는 Article 자동 삭제
        Article article = articleRepository.findByPersonalRecordId(personalRecordId);
        Long articleId = article.getId();
        articleRemoveService.removeArticle(articleId);

        validPersonalRecord.modifyPersonalRecord(personalRecordRequestDTO);
        return PersonalRecordResponseDTO.from(validPersonalRecord);
    }
}
