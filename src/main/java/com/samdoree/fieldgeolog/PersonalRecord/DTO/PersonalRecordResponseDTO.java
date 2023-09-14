package com.samdoree.fieldgeolog.PersonalRecord.DTO;

import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalRecordResponseDTO {

    private Long id;
    private String recordTitle;
    private LocalDateTime createDT;
    private LocalDateTime modifyDT;


    public PersonalRecordResponseDTO(PersonalRecord personalRecord) {
        this.id = personalRecord.getId();
        this.recordTitle = personalRecord.getRecordTitle();
        this.createDT = personalRecord.getCreateDT();
        this.modifyDT = personalRecord.getModifyDT();
    }

    public static PersonalRecordResponseDTO from(PersonalRecord personalRecord) {
        return new PersonalRecordResponseDTO(personalRecord);
    }
}
