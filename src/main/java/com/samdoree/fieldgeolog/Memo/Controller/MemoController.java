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
@RequiredArgsConstructor
public class MemoController {

    private final MemoRegisterService memoRegisterService;
    private final MemoSearchService memoSearchService;
    private final MemoModifyService memoModifyService;
    private final MemoRemoveService memoRemoveService;

    @PostMapping("/api/spots/{spotId}/memos")
    public MemoResponseDTO addMemo(@PathVariable Long spotId, @Valid @RequestBody MemoRequestDTO memoRequestDTO) throws Exception {
        return memoRegisterService.addMemo(spotId, memoRequestDTO);
    }

    @GetMapping("/api/spots/{spotId}/memos")
    public List<MemoResponseDTO> getAllMemoList(@PathVariable Long spotId) {
        return memoSearchService.getAllMemoList(spotId);
    }

    @GetMapping("/api/spots/{spotId}/memos/{memoId}")
    public MemoResponseDTO getOneMemo(@PathVariable Long spotId, @PathVariable Long memoId) {
        return memoSearchService.getOneMemo(spotId, memoId);
    }

    @PatchMapping("/api/spots/{spotId}/memos/{memoId}")
    public MemoResponseDTO modifyMemo(@PathVariable Long spotId, @PathVariable Long memoId, @RequestBody MemoRequestDTO memoRequestDTO) {
        return memoModifyService.modifyMemo(spotId, memoId, memoRequestDTO);
    }

    @DeleteMapping("/api/spots/{spotId}/memos/{memoId}")
    public Boolean removeMemo(@PathVariable Long spotId, @PathVariable Long memoId) {
        return memoRemoveService.removeMemo(spotId, memoId);
    }
}
