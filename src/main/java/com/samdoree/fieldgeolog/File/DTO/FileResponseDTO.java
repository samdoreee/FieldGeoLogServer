package com.samdoree.fieldgeolog.File.DTO;

import com.samdoree.fieldgeolog.File.Entity.File;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@NoArgsConstructor
public class FileResponseDTO {

    private Long id;
    private String fileName;
    private String filePath;

    public FileResponseDTO(File file) {
        this.id = file.getId();
        this.fileName = file.getFileName();
        this.filePath = file.getFilePath();
    }
}
