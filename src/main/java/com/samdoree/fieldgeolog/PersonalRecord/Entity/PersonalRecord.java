package com.samdoree.fieldgeolog.PersonalRecord.Entity;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordRequestDTO;
import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Thumbnail.Entity.Thumbnail;
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

    @OneToOne(mappedBy = "personalRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private Thumbnail thumbnail;
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

    private PersonalRecord(PersonalRecordRequestDTO personalRecordRequestDTO) {
        this.recordTitle = personalRecordRequestDTO.getRecordTitle();
        this.createDT = LocalDateTime.now();
        this.modifyDT = LocalDateTime.now();
        this.isValid = true;
    }

    public void modifyPersonalRecord(PersonalRecordRequestDTO personalRecordRequestDTO) {
        this.recordTitle = personalRecordRequestDTO.getRecordTitle();
        this.modifyDT = LocalDateTime.now();
    }

    // 썸네일 사진 update하기
//    public Thumbnail updateThumbnailPicture() {
//
//        List<Spot> spots = this.getSpotList();
//        if (!spots.isEmpty()) {
//            for (Spot spot : spots) {
//                List<Memo> memos = spot.getMemoList();
//                if (!memos.isEmpty()) {
//                    for (Memo memo : memos) {
//                        List<Picture> pictures = memo.getPictureList();
//                        if (!pictures.isEmpty()) {
//                            for (Picture picture : pictures) {
//                                if (picture.isValid()) {
//                                    // 유효한 Picture를 찾으면 새로운 썸네일로 설정
//                                    Thumbnail newThumbnail = Thumbnail.createFrom(this, null, picture);
//                                    thumbnailPath = newThumbnail.getFilePath();
//                                    return newThumbnail; // 썸네일 설정이 완료되었으므로 반복 종료
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        Thumbnail newThumbnail = Thumbnail.createFrom(this, null, null);
//        thumbnailPath = newThumbnail.getFilePath();
//        return newThumbnail;
//    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
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

    public void belongToThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }
}
