package com.samdoree.fieldgeolog.Article.Service;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import com.samdoree.fieldgeolog.Comment.Entity.Comment;
import com.samdoree.fieldgeolog.Comment.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleRemoveService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public boolean removeArticle(Long articleId) {

        Article validArticle = articleRepository.findById(articleId)
                .filter(article -> article.isValid())
                .orElseThrow(() -> new NoSuchElementException("Article not found or is not valid."));

        // Article과 1:N 연관관계를 맺는 Comment 객체의 isValid 속성을 모두 false로 설정
        if (commentRepository.existsByArticleId(articleId)) {
            List<Comment> commentList = commentRepository.findAllByArticleId(articleId)
                    .stream()
                    .filter(comment -> comment.isValid())
                    .collect(Collectors.toList());

            for (Comment comment : commentList) {
                comment.markAsInvalid();
                commentRepository.save(comment);
            }
        }

        validArticle.markAsInvalid();
        articleRepository.save(validArticle);
        return true;
    }
}


