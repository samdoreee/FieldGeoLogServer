package com.samdoree.fieldgeolog.Article.Service;

import com.samdoree.fieldgeolog.Article.DTO.ArticleResponseDTO;
import com.samdoree.fieldgeolog.Article.DTO.ArticleRequestDTO;
import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.PersonalRecord.Service.PersonalRecordSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleRegisterService {

    private final ArticleRepository articleRepository;
    private final PersonalRecordRepository personalRecordRepository;
    private final PersonalRecordSearchService personalRecordSearchService;


    @Transactional
    public ArticleResponseDTO addArticle(ArticleRequestDTO articleRequestDTO) throws Exception {

        Long personalRecordId = articleRequestDTO.getRecordId();
        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());

        Article article = articleRepository.save(Article.createFrom(articleRequestDTO, personalRecord));
        PersonalRecordResponseDTO personalRecordResponseDTO = personalRecordSearchService.getOnePersonalRecord(personalRecordId);
        return ArticleResponseDTO.fromPersonalRecord(article, personalRecordResponseDTO);
    }
}
