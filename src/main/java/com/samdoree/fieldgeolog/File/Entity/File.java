package com.samdoree.fieldgeolog.File.Entity;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "memo_image")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id")
    private Memo memo;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Builder
    private File(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public static File createFile(MultipartFile multipartFile, String fileDir, Long spotId, Long memoId) {
        UUID uuid = UUID.randomUUID();
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String filePath = uuid + "." + extension;
        java.io.File targetFile = new java.io.File(fileDir + spotId + "/" + memoId + "/" + filePath);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile); //지움
            e.printStackTrace();
        }

        return File.builder()
                .filePath(filePath)
                .fileName(multipartFile.getOriginalFilename())
                .build();
    }

    //== 연관관계 메서드 ==//
    public void belongToMemo(Memo memo) {
        this.memo = memo;
    }
}
