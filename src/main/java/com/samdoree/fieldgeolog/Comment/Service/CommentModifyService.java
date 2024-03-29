package com.samdoree.fieldgeolog.Comment.Service;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Article.Repository.ArticleRepository;
import com.samdoree.fieldgeolog.Comment.Entity.Comment;
import com.samdoree.fieldgeolog.Comment.DTO.CommentRequestDTO;
import com.samdoree.fieldgeolog.Comment.DTO.CommentResponseDTO;
import com.samdoree.fieldgeolog.Comment.Repository.CommentRepository;
import com.samdoree.fieldgeolog.User.Entity.User;
import com.samdoree.fieldgeolog.User.Repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentModifyService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDTO modifyComment(Long articleId, Long commentId, CommentRequestDTO commentRequestDTO) throws Exception {

        Article validArticle = articleRepository.findById(articleId)
                .filter(Article::isValid)
                .orElseThrow(() -> new NoSuchElementException("Article not found or is not valid."));
        Comment validComment = commentRepository.findById(commentId)
                .filter(Comment::isValid)
                .orElseThrow(() -> new NoSuchElementException("Comment not found or is not valid."));
        User validUser = userRepository.findById(commentRequestDTO.getUserId())
            .filter(User::isValid)
            .orElseThrow(() -> new NoSuchElementException("User not found or is not valid."));

        validComment.modifyComment(commentRequestDTO);
        return CommentResponseDTO.from(validComment);
    }
}
