package com.samdoree.fieldgeolog.Comment.Service;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import com.samdoree.fieldgeolog.Comment.Entity.Comment;
import com.samdoree.fieldgeolog.Comment.DTO.CommentRequestDTO;
import com.samdoree.fieldgeolog.Comment.DTO.CommentResponseDTO;
import com.samdoree.fieldgeolog.Comment.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentModifyService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDTO modifyComment(Long articleId, Long commentId, CommentRequestDTO commentRequestDTO) throws Exception {

        Article validArticle = articleRepository.findById(articleId)
                .filter(article -> article.isValid())
                .orElseThrow(() -> new NoSuchElementException("Article not found or is not valid."));
        Comment validComment = commentRepository.findById(commentId)
                .filter(comment -> comment.isValid())
                .orElseThrow(() -> new NoSuchElementException("Comment not found or is not valid."));

        validComment.modifyComment(commentRequestDTO);
        return CommentResponseDTO.from(validComment);
    }
}
