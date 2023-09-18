package com.samdoree.fieldgeolog.Picture.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PictureRequestDTO {

    //Q. @NotNull 필요할까?
    private String fileName;
    private String filePath;
}
