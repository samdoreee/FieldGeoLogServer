package com.samdoree.fieldgeolog.Picture.Repository;

import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findAllByMemoId(Long memoId);
}
