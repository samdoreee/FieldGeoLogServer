package com.samdoree.fieldgeolog.Picture.Repository;

import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findAllByMemoId(Long memoId);
    Boolean existsByMemoId(Long memoId);

}
