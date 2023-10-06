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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

        List<Article> validArticleList = articleRepository.findAll()
                .stream()
                .filter(Article::isValid)
                .collect(Collectors.toList());

        return validArticleList.stream()
                .map(article -> {
                    Long personalRecordId = article.getPersonalRecord().getId();
                    PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                            .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
                    return new ArticleResponseDTO(article, PersonalRecordResponseDTO.from(personalRecord));
                })
                .collect(Collectors.toList());
    }

    public List<ArticleResponseDTO> sortAllArticleOrderByASC() {

        List<Article> validArticleList = articleRepository.findAll(Sort.by(Sort.Direction.ASC, "createDT"))
                .stream()
                .filter(Article::isValid)
                .collect(Collectors.toList());

        return validArticleList.stream()
                .map(article -> {
                    Long personalRecordId = article.getPersonalRecord().getId();
                    PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                            .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
                    return new ArticleResponseDTO(article, PersonalRecordResponseDTO.from(personalRecord));
                })
                .collect(Collectors.toList());
    }

    public List<ArticleResponseDTO> sortAllArticleOrderByDESC() {

        List<Article> validArticleList = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createDT"))
                .stream()
                .filter(Article::isValid)
                .collect(Collectors.toList());

        return validArticleList.stream()
                .map(article -> {
                    Long personalRecordId = article.getPersonalRecord().getId();
                    PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                            .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
                    return new ArticleResponseDTO(article, PersonalRecordResponseDTO.from(personalRecord));
                })
                .collect(Collectors.toList());
    }

    // 제목 기반 검색
    public List<ArticleResponseDTO> searchByTitle(String keyword) {

        List<Article> validArticleList = articleRepository.findByTitleContaining(keyword)
            .stream()
            .filter(Article::isValid)
            .collect(Collectors.toList());

        return validArticleList.stream()
            .map(article -> {
                Long personalRecordId = article.getPersonalRecord().getId();
                PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                    .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
                return new ArticleResponseDTO(article, PersonalRecordResponseDTO.from(personalRecord));
            })
            .collect(Collectors.toList());
    }

    // 닉네임 기반 검색
    public List<ArticleResponseDTO> searchByNickname(String nickname) {

        List<Article> validArticleList = articleRepository.findByNicknameContaining(nickname)
            .stream()
            .filter(Article::isValid)
            .collect(Collectors.toList());

        return validArticleList.stream()
            .map(article -> {
                Long personalRecordId = article.getPersonalRecord().getId();
                PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                    .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
                return new ArticleResponseDTO(article, PersonalRecordResponseDTO.from(personalRecord));
            })
            .collect(Collectors.toList());
    }

    // 검색 유형이 잘못된 경우 빈 목록을 반환하는 메서드
    public List<ArticleResponseDTO> emptySearchResult() {
        // 빈 목록을 반환한다.
        return Collections.emptyList();
    }

    public ArticleResponseDTO getOneArticle(Long articleId) {

        Article validArticle = articleRepository.findById(articleId)
                .filter(Article::isValid)
                .orElseThrow(() -> new NoSuchElementException("Article not found or is not valid."));

        Long personalRecordId = validArticle.getPersonalRecord().getId();
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

        return ArticleResponseDTO.fromPersonalRecord(validArticle, personalRecordResponseDTO);
    }

}
