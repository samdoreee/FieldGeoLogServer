package com.samdoree.fieldgeolog.Memo.Entity;

import com.samdoree.fieldgeolog.Memo.DTO.MemoRequestDTO;
import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    // Memo 엔터티와 Picture 엔터티 간의 1:N 관계 설정
    // "memo"는 Picture 엔터티의 memo 필드 이름과 동일해야 합니다.
    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Picture> pictureList = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean isValid;

    public static Memo createFrom(Spot spot, MemoRequestDTO memoRequestDTO) {
        return Memo.builder()
                .spot(spot)
                .description(memoRequestDTO.getDescription())
                .isValid(true)
                .build();
    }

    public void modifyMemo(MemoRequestDTO memoRequestDTO) {
        this.description = memoRequestDTO.getDescription();
    }

    //== 유효성 필드 메서드 ==//
    public void markAsInvalid() {
        this.isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    //== 연관관계 메서드 ==//
    public void addPicture(Picture picture) {
        pictureList.add(picture);
        picture.belongToMemo(this);
    }

    public void removePicture() {
        pictureList.clear();
    }

    public void belongToSpot(Spot spot) {
        this.spot = spot;
    }
}