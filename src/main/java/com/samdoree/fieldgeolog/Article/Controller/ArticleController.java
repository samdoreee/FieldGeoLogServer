package com.samdoree.fieldgeolog.Article.Controller;

import com.samdoree.fieldgeolog.Article.DTO.ArticleResponseDTO;
import com.samdoree.fieldgeolog.Article.DTO.ArticleRequestDTO;
import com.samdoree.fieldgeolog.Article.Service.ArticleRegisterService;
import com.samdoree.fieldgeolog.Article.Service.ArticleRemoveService;
import com.samdoree.fieldgeolog.Article.Service.ArticleSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRegisterService articleRegisterService;
    private final ArticleSearchService articleSearchService;
    private final ArticleRemoveService articleRemoveService;


    @PostMapping("/api/articles")
    public ArticleResponseDTO addArticle(@Valid @RequestBody ArticleRequestDTO articleRequestDTO) throws Exception {
        return articleRegisterService.addArticle(articleRequestDTO);
    }

    @GetMapping("/api/articles")
    public List<ArticleResponseDTO> getAllArticleList(@RequestParam(name = "sortBy", required = false) String sortBy) {
        if ("asc".equalsIgnoreCase(sortBy)) {   // api/articles?sortBy=asc
            return articleSearchService.sortAllArticleOrderByASC();
        } else if ("desc".equalsIgnoreCase(sortBy)) {   // api/articles?sortBy=desc
            return articleSearchService.sortAllArticleOrderByDESC();
        } else {
            // 기본 동작: 정렬되지 않은 목록 반환
            return articleSearchService.getAllArticleList();
        }
    }

    @GetMapping("/api/articles/search")
    public List<ArticleResponseDTO> searchArticles(
            @RequestParam("searchType") String searchType,
            @RequestParam("keyword") String keyword) {

        List<ArticleResponseDTO> searchResults;

        if ("title".equals(searchType)) {   // 제목 기반 검색
            searchResults = articleSearchService.searchByTitle(keyword);
//        } else if ("nickname".equals(searchType)) { // 닉네임 기반 검색
//            searchResults = articleSearchService.searchByNickname(keyword);
        } else {
            searchResults = articleSearchService.emptySearchResult();
        }

        return searchResults;
    }

    @GetMapping("/api/articles/{articleId}")
    public ArticleResponseDTO getOneArticle(@PathVariable Long articleId) {
        return articleSearchService.getOneArticle(articleId);
    }

    @DeleteMapping("/api/articles/{articleId}")
    public Boolean removeArticle(@PathVariable Long articleId) {
        return articleRemoveService.removeArticle(articleId);
    }
}
