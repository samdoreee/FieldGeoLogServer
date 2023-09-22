package com.samdoree.fieldgeolog.Article.Service;

import com.samdoree.fieldgeolog.Article.DTO.ArticleResponseDTO;
import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import com.samdoree.fieldgeolog.Memo.DTO.MemoResponseDTO;
import com.samdoree.fieldgeolog.Memo.Service.MemoSearchService;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.PersonalRecord.Service.PersonalRecordSearchService;
import com.samdoree.fieldgeolog.Picture.DTO.PictureResponseDTO;
import com.samdoree.fieldgeolog.Picture.Service.PictureSearchService;
import com.samdoree.fieldgeolog.Spot.DTO.SpotResponseDTO;
import com.samdoree.fieldgeolog.Spot.Service.SpotSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleSearchService {

    private final ArticleRepository articleRepository;
    private final PersonalRecordRepository personalRecordRepository;
    private final PersonalRecordSearchService personalRecordSearchService;
    private final SpotSearchService spotSearchService;
    private final MemoSearchService memoSearchService;
    private final PictureSearchService pictureSearchService;

    public List<ArticleResponseDTO> getAllArticleList() {
        List<Article> articleList = articleRepository.findAll();

        return articleList.stream()
                .map(article -> {
                    Long personalRecordId = article.getPersonalRecord().getId();
                    PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                            .orElse(null); // PersonalRecord가 없는 경우에 대한 예외 처리 필요
                    return new ArticleResponseDTO(article, PersonalRecordResponseDTO.from(personalRecord));
                })
                .collect(Collectors.toList());
    }


    public ArticleResponseDTO getOneArticle(Long articleId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NullPointerException());

        Long personalRecordId = article.getPersonalRecord().getId();
        PersonalRecordResponseDTO personalRecordResponseDTO = personalRecordSearchService.getOnePersonalRecord(personalRecordId);

        // PersonalRecord -> Spot
        List<SpotResponseDTO> spotResponseDTOList = spotSearchService.getAllSpotList(personalRecordId);
        List<SpotResponseDTO> updatedSpotResponseDTOList = new ArrayList<>();   // 새로운 리스트 생성

        for (SpotResponseDTO spotResponseDTO : spotResponseDTOList) {
            Long spotId = spotResponseDTO.getId();

            // Memo
            List<MemoResponseDTO> memoResponseDTOList = memoSearchService.getAllMemoList(personalRecordId, spotId);
            List<MemoResponseDTO> updatedMemoResponseDTOList = new ArrayList<>(); // 새로운 리스트 생성

            for (MemoResponseDTO memoResponseDTO : memoResponseDTOList) {
                Long memoId = memoResponseDTO.getId();

                // Picture
                List<PictureResponseDTO> pictureResponseDTOList = pictureSearchService.getAllPictureList(personalRecordId, spotId, memoId);
                List<PictureResponseDTO> updatedPictureResponseDTOList = new ArrayList<>(); // 새로운 리스트 생성

                for (PictureResponseDTO pictureResponseDTO : pictureResponseDTOList) {
                    updatedPictureResponseDTOList.add(pictureResponseDTO);
                }
                memoResponseDTO.setPictureResponseDTOList(updatedPictureResponseDTOList);
                updatedMemoResponseDTOList.add(memoResponseDTO);
            }
            spotResponseDTO.setMemoResponseDTOList(updatedMemoResponseDTOList);
            updatedSpotResponseDTOList.add(spotResponseDTO);
        }
        personalRecordResponseDTO.setSpotResponseDTOList(updatedSpotResponseDTOList);

        return ArticleResponseDTO.fromPersonalRecord(article, personalRecordResponseDTO);
    }

}
