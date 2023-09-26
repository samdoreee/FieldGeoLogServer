package com.samdoree.fieldgeolog.Comment.Service;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import com.samdoree.fieldgeolog.Comment.Entity.Comment;
import com.samdoree.fieldgeolog.Comment.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentRemoveService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public boolean removeComment(Long articleId, Long commentId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NullPointerException());
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException());

        comment.markAsInvalid();
        commentRepository.save(comment);
        return true;
    }
}
