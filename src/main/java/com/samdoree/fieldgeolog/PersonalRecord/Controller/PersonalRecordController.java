package com.samdoree.fieldgeolog.PersonalRecord.Controller;

import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRequestDTO;
import com.samdoree.fieldgeolog.PersonalRecord.Service.PersonalRecordModifyService;
import com.samdoree.fieldgeolog.PersonalRecord.Service.PersonalRecordRegisterService;
import com.samdoree.fieldgeolog.PersonalRecord.Service.PersonalRecordRemoveService;
import com.samdoree.fieldgeolog.PersonalRecord.Service.PersonalRecordSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonalRecordController {

    private final PersonalRecordRegisterService personalRecordRegisterService;
    private final PersonalRecordSearchService personalRecordSearchService;
    private final PersonalRecordModifyService personalRecordModifyService;
    private final PersonalRecordRemoveService personalRecordRemoveService;


    @PostMapping("/api/personalRecords")
    public PersonalRecordResponseDTO addPersonalRecord(@Valid @RequestBody PersonalRequestDTO personalRequestDTO) throws Exception {
        return personalRecordRegisterService.addPersonalRecord(personalRequestDTO);
    }

    @GetMapping("/api/personalRecords")
    public List<PersonalRecordResponseDTO> getAllPersonalRecordList() {
        return personalRecordSearchService.getAllPersonalRecordList();
    }

    @GetMapping("/api/personalRecords/{personalRecordId}")
    public PersonalRecordResponseDTO getOnePersonalRecord(@PathVariable Long personalRecordId) {
        return personalRecordSearchService.getOnePersonalRecord(personalRecordId);
    }

    @PatchMapping("/api/personalRecords/{personalRecordId}")
    public PersonalRecordResponseDTO modifyPersonalRecord(@PathVariable Long personalRecordId, @Valid @RequestBody PersonalRequestDTO personalRequestDTO) throws Exception {
        return personalRecordModifyService.modifyPersonalRecord(personalRecordId, personalRequestDTO);
    }

    @DeleteMapping("/api/personalRecords/{personalRecordId}")
    public Boolean removePersonalRecord(@PathVariable Long personalRecordId) {
        return personalRecordRemoveService.removePersonalRecord(personalRecordId);
    }
}
