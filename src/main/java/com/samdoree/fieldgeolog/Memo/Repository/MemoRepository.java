package com.samdoree.fieldgeolog.Memo.Repository;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllBySpotId(Long spotId);
}
