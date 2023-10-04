package com.samdoree.fieldgeolog.PersonalRecord.DTO;

import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.Spot.DTO.SpotResponseDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalRecordResponseDTO {

    private Long id;
    private String recordTitle;
    private LocalDateTime createDT;
    private LocalDateTime modifyDT;
    private List<SpotResponseDTO> spotResponseDTOList;
    private String fileName;


    public PersonalRecordResponseDTO(PersonalRecord personalRecord) {
        this.id = personalRecord.getId();
        this.recordTitle = personalRecord.getRecordTitle();
        this.createDT = personalRecord.getCreateDT();
        this.modifyDT = personalRecord.getModifyDT();
        this.spotResponseDTOList = personalRecord.getSpotList().stream().map(SpotResponseDTO::new).collect(Collectors.toList());
        this.fileName = personalRecord.getFileName();
    }

    public static PersonalRecordResponseDTO from(PersonalRecord personalRecord) {
        return new PersonalRecordResponseDTO(personalRecord);
    }
}
