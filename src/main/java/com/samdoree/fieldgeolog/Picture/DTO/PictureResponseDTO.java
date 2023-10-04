package com.samdoree.fieldgeolog.Picture.DTO;

import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureResponseDTO {

    private Long id;
    private String fileFolder;
    private String fileName;

    public PictureResponseDTO(Picture picture) {
        this.id = picture.getId();
        this.fileFolder = picture.getFileFolder();
        this.fileName = picture.getFileName();
    }

    public static PictureResponseDTO from(Picture picture) {
        return new PictureResponseDTO(picture);
    }
}