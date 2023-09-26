package com.samdoree.fieldgeolog.PersonalRecord.Controller;

import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordResponseDTO;
import com.samdoree.fieldgeolog.PersonalRecord.DTO.PersonalRecordRequestDTO;
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
    public PersonalRecordResponseDTO addPersonalRecord(@Valid @RequestBody PersonalRecordRequestDTO personalRecordRequestDTO) throws Exception {
        return personalRecordRegisterService.addPersonalRecord(personalRecordRequestDTO);
    }

    @GetMapping("/api/personalRecords")
    public List<PersonalRecordResponseDTO> getAllPersonalRecordList(@RequestParam(name = "sortBy", required = false) String sortBy) {

        if ("asc".equalsIgnoreCase(sortBy)) {   // api/personalRecords?sortBy=asc
            return personalRecordSearchService.sortAllPersonalRecordOrderByASC();
        } else if ("desc".equalsIgnoreCase(sortBy)) {   // api/personalRecords?sortBy=desc
            return personalRecordSearchService.sortAllPersonalRecordOrderByDESC();
        } else {
            // 기본 동작: 정렬되지 않은 목록 반환
            return personalRecordSearchService.getAllPersonalRecordList();
        }
    }

    @GetMapping("/api/personalRecords/{personalRecordId}")
    public PersonalRecordResponseDTO getOnePersonalRecord(@PathVariable Long personalRecordId) {
        return personalRecordSearchService.getOnePersonalRecord(personalRecordId);
    }

    @PatchMapping("/api/personalRecords/{personalRecordId}")
    public PersonalRecordResponseDTO modifyPersonalRecord(@PathVariable Long personalRecordId, @Valid @RequestBody PersonalRecordRequestDTO personalRecordRequestDTO) throws Exception {
        return personalRecordModifyService.modifyPersonalRecord(personalRecordId, personalRecordRequestDTO);
    }

    @DeleteMapping("/api/personalRecords/{personalRecordId}")
    public Boolean removePersonalRecord(@PathVariable Long personalRecordId) {
        return personalRecordRemoveService.removePersonalRecord(personalRecordId);
    }
}
