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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentSearchService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public List<CommentResponseDTO> getAllCommentList(Long articleId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NullPointerException());

        List<Comment> comments = commentRepository.findAllByArticleId(articleId);
        return comments.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
    }

    public CommentResponseDTO getOneComment(Long articleId, Long commentId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NullPointerException());
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException());

        return CommentResponseDTO.from(comment);
    }
}
