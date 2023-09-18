package com.samdoree.fieldgeolog.Memo.Controller;

import com.samdoree.fieldgeolog.Memo.DTO.MemoRequestDTO;
import com.samdoree.fieldgeolog.Memo.DTO.MemoResponseDTO;
import com.samdoree.fieldgeolog.Memo.Service.MemoModifyService;
import com.samdoree.fieldgeolog.Memo.Service.MemoRegisterService;
import com.samdoree.fieldgeolog.Memo.Service.MemoRemoveService;
import com.samdoree.fieldgeolog.Memo.Service.MemoSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/personalRecords/{personalRecordId}/spots/{spotId}/memos")
@RequiredArgsConstructor
public class MemoController {

    private final MemoRegisterService memoRegisterService;
    private final MemoSearchService memoSearchService;
    private final MemoModifyService memoModifyService;
    private final MemoRemoveService memoRemoveService;

    @PostMapping()
    public MemoResponseDTO addMemo(@PathVariable Long personalRecordId, @PathVariable Long spotId, @Valid @RequestBody MemoRequestDTO memoRequestDTO) throws Exception {
        return memoRegisterService.addMemo(personalRecordId, spotId, memoRequestDTO);
    }

    @GetMapping()
    public List<MemoResponseDTO> getAllMemoList(@PathVariable Long personalRecordId, @PathVariable Long spotId) {
        return memoSearchService.getAllMemoList(personalRecordId, spotId);
    }

    @GetMapping("/{memoId}")
    public MemoResponseDTO getOneMemo(@PathVariable Long personalRecordId, @PathVariable Long spotId, @PathVariable Long memoId) {
        return memoSearchService.getOneMemo(personalRecordId, spotId, memoId);
    }

    @PatchMapping("/{memoId}")
    public MemoResponseDTO modifyMemo(@PathVariable Long personalRecordId, @PathVariable Long spotId, @PathVariable Long memoId, @Valid @RequestBody MemoRequestDTO memoRequestDTO) {
        return memoModifyService.modifyMemo(personalRecordId, spotId, memoId, memoRequestDTO);
    }

    @DeleteMapping("/{memoId}")
    public Boolean removeMemo(@PathVariable Long personalRecordId, @PathVariable Long spotId, @PathVariable Long memoId) {
        return memoRemoveService.removeMemo(personalRecordId, spotId, memoId);
    }

}