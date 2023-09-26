package com.samdoree.fieldgeolog.Picture.Entity;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Picture.DTO.PictureRequestDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id")
    private Memo memo;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    private Boolean isValid;

    public static Picture createFrom(Memo memo, PictureRequestDTO pictureRequestDTO) {
        return Picture.builder()
                .memo(memo)
                .fileName(pictureRequestDTO.getFileName())
                .filePath(pictureRequestDTO.getFilePath())
                .isValid(true)
                .build();
    }

    public void modifyPicture(PictureRequestDTO pictureRequestDTO) {
        this.fileName = pictureRequestDTO.getFileName();
        this.filePath = pictureRequestDTO.getFilePath();
    }

    //== 유효성 필드 메서드 ==//
    public void markAsInvalid() {
        this.isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    //== 연관관계 메서드 ==//
    public void belongToMemo(Memo memo) {
        this.memo = memo;
    }
}