package com.samdoree.fieldgeolog.Comment.Service;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import com.samdoree.fieldgeolog.Comment.DTO.CommentResponseDTO;
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
public class CommentSearchService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public List<CommentResponseDTO> getAllCommentList(Long articleId) {

        Article validArticle = articleRepository.findById(articleId)
                .filter(article -> article.isValid())
                .orElseThrow(() -> new NoSuchElementException("Article not found or is not valid."));

        List<Comment> validCommentList = commentRepository.findAllByArticleId(articleId)
                .stream()
                .filter(comment -> comment.isValid())
                .collect(Collectors.toList());

        return validCommentList.stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public CommentResponseDTO getOneComment(Long articleId, Long commentId) {

        Article validArticle = articleRepository.findById(articleId)
                .filter(article -> article.isValid())
                .orElseThrow(() -> new NoSuchElementException("Article not found or is not valid."));
        Comment validComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found or is not valid."));

        return CommentResponseDTO.from(validComment);
    }
}
