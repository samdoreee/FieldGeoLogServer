package com.samdoree.fieldgeolog.Memo.Controller;

import com.samdoree.fieldgeolog.Memo.DTO.MemoRequestDTO;
import com.samdoree.fieldgeolog.Memo.DTO.MemoResponseDTO;
import com.samdoree.fieldgeolog.Memo.Service.MemoModifyService;
import com.samdoree.fieldgeolog.Memo.Service.MemoRegisterService;
import com.samdoree.fieldgeolog.Memo.Service.MemoRemoveService;
import com.samdoree.fieldgeolog.Memo.Service.MemoSearchService;
import com.samdoree.fieldgeolog.File.Service.FileSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoRegisterService memoRegisterService;
    private final MemoSearchService memoSearchService;
    private final MemoModifyService memoModifyService;
    private final MemoRemoveService memoRemoveService;
    private final FileSearchService fileSearchService;

    @PostMapping(value = "/api/spots/{spotId}/memos", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public MemoResponseDTO addMemo(@PathVariable Long spotId, @RequestPart(value = "memo") @Valid MemoRequestDTO memoRequestDTO, @RequestPart(value = "file", required = false) MultipartFile[] multipartFiles) throws Exception {
        return memoRegisterService.addMemo(spotId, memoRequestDTO, multipartFiles);
    }

    @GetMapping("/api/spots/{spotId}/memos")
    public List<MemoResponseDTO> getAllMemoList(@PathVariable Long spotId) {
        return memoSearchService.getAllMemoList(spotId);
    }

    @GetMapping("/api/spots/{spotId}/memos/{memoId}")
    public MemoResponseDTO getOneMemo(@PathVariable Long spotId, @PathVariable Long memoId) {
        return memoSearchService.getOneMemo(spotId, memoId);
    }

    @PatchMapping(value = "/api/spots/{spotId}/memos/{memoId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public MemoResponseDTO modifyMemo(@PathVariable Long spotId, @PathVariable Long memoId, @RequestPart(value = "memo", required = false) @Valid MemoRequestDTO memoRequestDTO, MultipartFile[] multipartFiles) {
        return memoModifyService.modifyMemo(spotId, memoId, memoRequestDTO, multipartFiles);
    }

    @DeleteMapping("/api/spots/{spotId}/memos/{memoId}")
    public Boolean removeMemo(@PathVariable Long spotId, @PathVariable Long memoId) {
        return memoRemoveService.removeMemo(spotId, memoId);
    }

    //파일 다운로드
    @GetMapping("/api/spots/{spotId}/memos/{memoId}/files/{fileName:.+}")
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable Long spotId, @PathVariable Long memoId, @PathVariable String fileName) throws Exception {
        return fileSearchService.downloadFile(spotId, memoId, fileName);
    }
}