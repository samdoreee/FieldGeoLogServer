package com.samdoree.fieldgeolog.PersonalRecord.Service;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import com.samdoree.fieldgeolog.Article.Service.ArticleRemoveService;
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
    private final ArticleRemoveService articleRemoveService;
    private final ArticleRepository articleRepository;

    @Transactional
    public PersonalRecordResponseDTO modifyPersonalRecord(Long personalRecordId, PersonalRequestDTO personalRequestDTO) throws Exception {

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());

        // 관련 article 자동 삭제
        // 삭제할 article Id 구하기 => 삭제할 personalRecordId를 FK로 가지고 있는 article을 찾아 걔의 id를 찾아야하는데...
        Article article = articleRepository.findByPersonalRecordId(personalRecordId);
        Long articleId = article.getId();
        articleRemoveService.removeArticle(articleId);

        personalRecord.modifyPersonalRecord(personalRequestDTO);
        return PersonalRecordResponseDTO.from(personalRecord);
    }
}
