package com.samdoree.fieldgeolog.File.Repository;

import com.samdoree.fieldgeolog.File.Entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByFilePath(String filePath);
}
