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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentModifyService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDTO modifyComment(Long articleId, Long commentId, CommentRequestDTO commentRequestDTO) throws Exception {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NullPointerException());
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException());

        comment.modifyComment(commentRequestDTO);
        return CommentResponseDTO.from(comment);
    }
}
