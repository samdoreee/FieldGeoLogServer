package com.samdoree.fieldgeolog.Article.Service;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleRemoveService {

    private final ArticleRepository articleRepository;

    @Transactional
    public boolean removeArticle(Long articleId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NullPointerException());

        articleRepository.deleteById(articleId);
        return true;
    }
}


