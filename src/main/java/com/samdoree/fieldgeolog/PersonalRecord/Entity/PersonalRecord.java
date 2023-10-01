package com.samdoree.fieldgeolog.PersonalRecord.Entity;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordRequestDTO;
import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.Picture.Entity.Thumbnail;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personalRecord_id")
    private Long id;

    @OneToMany(mappedBy = "personalRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Spot> spotList = new ArrayList<>();

    @OneToOne(mappedBy = "personalRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private Article article;

    // PersonalRecord 엔터티와 Thumbnail 엔터티 간의 1:1 관계 설정
    @OneToOne(mappedBy = "personalRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private Thumbnail thumbnail; // PersonalRecord와 연결된 썸네일 사진

    private String thumbnailPath;

    private Boolean isValid;

    @Column(name = "record_title")
    private String recordTitle;

    @CreatedDate
    private LocalDateTime createDT;

    @CreatedDate
    private LocalDateTime modifyDT;

    public static PersonalRecord createFrom(PersonalRecordRequestDTO personalRecordRequestDTO) {
        return new PersonalRecord(personalRecordRequestDTO);
    }

    public PersonalRecord(PersonalRecordRequestDTO personalRecordRequestDTO) {
        this.recordTitle = personalRecordRequestDTO.getRecordTitle();
        this.createDT = LocalDateTime.now();
        this.modifyDT = LocalDateTime.now();
        this.isValid = true;

        if (this.thumbnail == null) {
            thumbnailPath = "src/main/resources/Image/basicImage.jpg";
        }
    }

    public void modifyPersonalRecord(PersonalRecordRequestDTO personalRecordRequestDTO) {
        this.recordTitle = personalRecordRequestDTO.getRecordTitle();
        this.modifyDT = LocalDateTime.now();
        updateThumbnailPicture();
    }

    // 썸네일 사진 update하기
    public void updateThumbnailPicture() {
        Thumbnail newThumbnail = null; // 새로운 썸네일 객체를 초기화

        List<Spot> spots = this.getSpotList();
        if (!spots.isEmpty()) {
            for (Spot spot : spots) {
                List<Memo> memos = spot.getMemoList();
                if (!memos.isEmpty()) {
                    for (Memo memo : memos) {
                        List<Picture> pictures = memo.getPictureList();
                        if (!pictures.isEmpty()) {
                            for (Picture picture : pictures) {
                                if (picture.isValid()) {
                                    newThumbnail = picture.getThumbnail(); // 유효한 Picture를 찾으면 새로운 썸네일로 설정
                                    break; // 썸네일 설정이 완료되었으므로 반복 종료
                                }
                            }
                        }
                        if (newThumbnail != null) {
                            break; // 썸네일 설정이 완료되었으므로 반복 종료
                        }
                    }
                }
                if (newThumbnail != null) {
                    break; // 썸네일 설정이 완료되었으므로 반복 종료
                }
            }
        }

        // 새로운 썸네일이 설정되었으면 현재의 thumbnailPicture을 갱신
        if (newThumbnail != null) {
            this.thumbnail = newThumbnail;
            this.thumbnailPath = newThumbnail.getFilePath();
        } else {
            // 유효한 썸네일이 없을 경우, 기본 이미지로 유지
            this.thumbnail = createBasicImage();
            this.thumbnailPath = this.getThumbnailPath();
        }
    }

    // 기본 썸네일 생성 메서드
    private Thumbnail createBasicImage() {
        Thumbnail basicImage = new Thumbnail("basicImage", "src/main/resources/Image/basicImage.jpg");
        return basicImage;
    }

    // 유효성 필드 메서드
    public void markAsInvalid() {
        this.isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    // 연관관계 메서드
    public void addSpot(Spot spot) {
        spotList.add(spot);
        spot.belongToPersonalRecord(this);
    }

    public void removeSpot() {
        spotList.clear();
    }

    public void belongToThumbnail(Thumbnail thumbnailPicture) {
        this.thumbnail = thumbnailPicture;
    }
}
