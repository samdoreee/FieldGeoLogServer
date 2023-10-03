package com.samdoree.fieldgeolog.Thumbnail.Entity;

import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Thumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thumbnail_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "thumbnail_type")
    private ThumbnailType thumbnailType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personalRecord_id")
    private PersonalRecord personalRecord;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Column(name = "file_path")
    private String filePath;

    private Boolean isValid;


    public static Thumbnail createFrom(PersonalRecord personalRecord, Spot spot, Picture picture) {

        ThumbnailType type;
        String filePath;
        if (picture == null) {
            type = ThumbnailType.None;
            // personalRecord와 spot 모두 null인 경우 기본 이미지 경로를 설정함
            filePath = "src/main/resources/Image/basicImage.jpg";
        } else if (spot != null) {
            type = ThumbnailType.SPOT;
            // filePath를 spot의 썸네일 경로로 설정
            filePath = picture.getFilePath();
        } else {   //personalRecord != null
            type = ThumbnailType.PERSONAL_RECORD;
            // filePath를 personalRecord의 썸네일 경로로 설정
            filePath = picture.getFilePath();
        }

        Thumbnail thumbnail = Thumbnail.builder()
                .personalRecord(personalRecord)
                .spot(spot)
                .filePath(filePath)
                .thumbnailType(type)
                .isValid(true)
                .build();

        // Thumbnail 객체와 Picture 객체 간의 연관 관계 설정
        if (picture != null) {
            thumbnail.belongToPicture(picture);
            picture.addThumbnail(thumbnail);
        }

        // Thumbnail 객체와 Spot 객체 간의 연관 관계 설정
        if (spot != null) {
            thumbnail.belongToSpot(spot);
            spot.belongToThumbnail(thumbnail);
        }

        // Thumbnail 객체와 PersonalRecord 객체 간의 연관 관계 설정
        else if (personalRecord != null) {
            thumbnail.belongToPersonalRecord(personalRecord);
            personalRecord.belongToThumbnail(thumbnail);
        }
        return thumbnail;
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

    public void belongToSpot(Spot spot) {
        this.spot = spot;
    }

    public void belongToPicture(Picture picture) {
        this.picture = picture;
    }
}