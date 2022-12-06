package com.samdoree.fieldgeolog.File.Service;

import com.samdoree.fieldgeolog.File.Entity.File;
import com.samdoree.fieldgeolog.File.Repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class FileSearchService {

    private final FileRepository fileRepository;
    @Value("${fieldgeolog.fileDir}")
    private String fileDir;

    public ResponseEntity<InputStreamResource> downloadFile(Long spotId, Long memoId, String filePath) throws FileNotFoundException, Exception {

        Optional<File> fileEntity = fileRepository.findByFilePath(filePath);

        if (!fileEntity.isPresent()) {
            System.out.println("파일이 존재하지 않습니다");
            return null;
        }

        String path = fileDir + spotId + "/" + memoId + "/" + filePath;
        java.io.File file = new java.io.File(path);
        HttpHeaders header = new HttpHeaders();

        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileEntity.get().getFileName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok().headers(header).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }
}