package com.samdoree.fieldgeolog.Picture.DTO;

import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureResponseDTO {

    private Long id;
    private String fileName;
    private String filePath;

    public PictureResponseDTO(Picture picture) {
        this.id = picture.getId();
        this.fileName = picture.getFileName();
        this.filePath = picture.getFilePath();
    }

    public static PictureResponseDTO from(Picture picture) {
        return new PictureResponseDTO(picture);
    }
}