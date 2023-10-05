package com.samdoree.fieldgeolog.Article.Repository;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByPersonalRecordId(Long personalRecordId);
    List<Article> findByTitleContaining(String title);
    List<Article> findByNicknameContaining(String nickname);
    Boolean existsByPersonalRecordId(Long personalRecordId);
}