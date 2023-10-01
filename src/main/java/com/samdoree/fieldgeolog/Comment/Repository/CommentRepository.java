package com.samdoree.fieldgeolog.Comment.Repository;

import com.samdoree.fieldgeolog.Comment.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;


@EnableJpaAuditing
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByArticleId(Long articleId);
    Boolean existsByArticleId(Long articleId);
}