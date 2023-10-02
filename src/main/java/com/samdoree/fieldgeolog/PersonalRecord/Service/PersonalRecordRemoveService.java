package com.samdoree.fieldgeolog.PersonalRecord.Service;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import com.samdoree.fieldgeolog.Spot.Service.SpotRemoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonalRecordRemoveService {

    private final PersonalRecordRepository personalRecordRepository;
    private final ArticleRepository articleRepository;
    private final SpotRepository spotRepository;
    private final SpotRemoveService spotRemoveService;

    @Transactional
    public boolean removePersonalRecord(Long personalRecordId) {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));

        // PersonalRecord와 1:1 연관관계를 맺는 Article 객체의 유효성 false로 자동 설정
        if (articleRepository.existsByPersonalRecordId(personalRecordId)) {

            Article article = articleRepository.findByPersonalRecordId(personalRecordId);
            article.markAsInvalid();
            articleRepository.save(article);
        }

        // PersonalRecord와 1:N 연관관계를 맺는 Spot 객체의 isValid 속성을 모두 false로 설정
        if (spotRepository.existsByPersonalRecordId(personalRecordId)) {

            List<Spot> spotList = spotRepository.findAllByPersonalRecordId(personalRecordId)
                    .stream()
                    .filter(spot -> spot.isValid())
                    .collect(Collectors.toList());

            for (Spot spot : spotList) {

                // Spot과 1:N 연관관계를 맺는 Memo 객체의 isValid 속성을 모두 false로 설정
                Long spotId = spot.getId();
                spotRemoveService.removeSpot(personalRecordId, spotId);

                spot.markAsInvalid();
                spotRepository.save(spot);
            }
        }

        validPersonalRecord.markAsInvalid();
        personalRecordRepository.save(validPersonalRecord);
        return true;
    }
}
