package com.samdoree.fieldgeolog.Article.Repository;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByPersonalRecordId(Long personalRecordId);
}