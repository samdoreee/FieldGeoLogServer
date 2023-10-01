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

import java.util.NoSuchElementException;

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

        // personalRecordId에 연관된 article이 이미 존재하는지 확인
        Boolean articleExists = articleRepository.existsByPersonalRecordId(personalRecordId);

        if (articleExists) {
            // 이미 article이 존재하면 예외 처리 또는 다른 로직을 수행할 수 있음
            throw new RuntimeException("PersonalRecord already has an associated article.");
        } else {
            PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                    .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));

            // 새로운 article 등록
            Article article = articleRepository.save(Article.createFrom(articleRequestDTO, personalRecord));
            PersonalRecordResponseDTO personalRecordResponseDTO = personalRecordSearchService.getOnePersonalRecord(personalRecordId);
            return ArticleResponseDTO.fromPersonalRecord(article, personalRecordResponseDTO);
        }
    }
}
