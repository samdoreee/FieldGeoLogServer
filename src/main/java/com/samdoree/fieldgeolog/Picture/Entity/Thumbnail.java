package com.samdoree.fieldgeolog.Picture.Entity;

import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.Picture.DTO.PictureRequestDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Thumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personalRecord_id")
    private PersonalRecord personalRecord;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    private Boolean isValid;

    public static Thumbnail createFrom(PictureRequestDTO pictureRequestDTO) {
        return Thumbnail.builder()
                .fileName(pictureRequestDTO.getFileName())
                .filePath(pictureRequestDTO.getFilePath())
                .isValid(true)
                .build();
    }

    public Thumbnail(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public void modifyThumbnail(PictureRequestDTO pictureRequestDTO) {
        this.fileName = pictureRequestDTO.getFileName();
        this.filePath = pictureRequestDTO.getFilePath();
    }

    public String getThumbnailPath(){
        return filePath;
    }

    public void updateFilePath(String filePath){
        this.filePath = filePath;
    }

    //== 유효성 필드 메서드 ==//
    public void markAsInvalid() {
        this.isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    //== 연관관계 메서드 ==//
    public void belongToPersonalRecord(PersonalRecord personalRecord) {
        this.personalRecord = personalRecord;
    }

    public void belongToPicture(Picture picture) {
        this.picture = picture;
    }
}