package com.samdoree.fieldgeolog.PersonalRecord.Entity;

import com.samdoree.fieldgeolog.Article.Entity.Article;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordRequestDTO;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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

    @OneToOne(mappedBy = "personalRecord", cascade = CascadeType.REMOVE)
    private Article article;

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
    }

    public void modifyPersonalRecord(PersonalRecordRequestDTO personalRecordRequestDTO) {
        this.recordTitle = personalRecordRequestDTO.getRecordTitle();
        this.modifyDT = LocalDateTime.now();
    }

    //== 유효성 필드 메서드 ==//
    public void markAsInvalid() {
        this.isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    //== 연관관계 메서드 ==//
    public void addSpot(Spot spot) {
        spotList.add(spot);
        spot.belongToPersonalRecord(this);
    }

    public void removeSpot() {
        spotList.clear();
    }

}
