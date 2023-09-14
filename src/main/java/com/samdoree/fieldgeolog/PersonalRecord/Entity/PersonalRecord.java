package com.samdoree.fieldgeolog.PersonalRecord.Entity;

import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRequestDTO;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(name = "record_title")  //50글자로 fix
    private String recordTitle;

    @CreatedDate
    private LocalDateTime createDT;

    @CreatedDate
    private LocalDateTime modifyDT;

    public static PersonalRecord createFrom(PersonalRequestDTO personalRequestDTO) {
        return new PersonalRecord(personalRequestDTO);
    }

    public PersonalRecord(PersonalRequestDTO personalRequestDTO) {
        this.recordTitle = personalRequestDTO.getRecordTitle();
        this.createDT = LocalDateTime.now();
        this.modifyDT = LocalDateTime.now();
        System.out.print(this.createDT);
    }

    public void modifyPersonalRecord(PersonalRequestDTO personalRequestDTO) {
        this.recordTitle = personalRequestDTO.getRecordTitle();
        this.modifyDT = LocalDateTime.now();
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
