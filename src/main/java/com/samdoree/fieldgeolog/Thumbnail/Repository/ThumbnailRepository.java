package com.samdoree.fieldgeolog.Thumbnail.Repository;

import com.samdoree.fieldgeolog.Thumbnail.Entity.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {
    List<Thumbnail> findByPictureId(Long pictureId);
}
