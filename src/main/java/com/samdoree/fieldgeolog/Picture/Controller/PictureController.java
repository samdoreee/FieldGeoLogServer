package com.samdoree.fieldgeolog.Picture.Controller;

import com.samdoree.fieldgeolog.Picture.DTO.PictureRequestDTO;
import com.samdoree.fieldgeolog.Picture.DTO.PictureResponseDTO;
import com.samdoree.fieldgeolog.Picture.Service.PictureModifyService;
import com.samdoree.fieldgeolog.Picture.Service.PictureRegisterService;
import com.samdoree.fieldgeolog.Picture.Service.PictureRemoveService;
import com.samdoree.fieldgeolog.Picture.Service.PictureSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/personalRecords/{personalRecordId}/spots/{spotId}/memos/{memoId}/pictures")
@RequiredArgsConstructor
public class PictureController {

    private final PictureRegisterService pictureRegisterService;
    private final PictureSearchService pictureSearchService;
    private final PictureModifyService pictureModifyService;
    private final PictureRemoveService pictureRemoveService;

    @PostMapping()
    public PictureResponseDTO addPicture(@PathVariable Long personalRecordId, @PathVariable Long spotId, @PathVariable Long memoId, @Valid @RequestBody PictureRequestDTO pictureRequestDTO) throws Exception {
        return pictureRegisterService.addPicture(personalRecordId, spotId, memoId, pictureRequestDTO);
    }

    @GetMapping()
    public List<PictureResponseDTO> getAllPictureList(@PathVariable Long personalRecordId, @PathVariable Long spotId, @PathVariable Long memoId) {
        return pictureSearchService.getAllPictureList(personalRecordId, spotId, memoId);
    }

    @GetMapping("/{pictureId}")
    public PictureResponseDTO getOnePicture(@PathVariable Long personalRecordId, @PathVariable Long spotId, @PathVariable Long memoId, @PathVariable Long pictureId) {
        return pictureSearchService.getOnePicture(personalRecordId, spotId, memoId, pictureId);
    }

    @PatchMapping("/{pictureId}")
    public PictureResponseDTO modifyPicture(@PathVariable Long personalRecordId, @PathVariable Long spotId, @PathVariable Long memoId, @PathVariable Long pictureId, @Valid @RequestBody PictureRequestDTO pictureRequestDTO) throws Exception {
        return pictureModifyService.modifyPicture(personalRecordId, spotId, memoId, pictureId, pictureRequestDTO);
    }

    @DeleteMapping("/{pictureId}")
    public Boolean removePicture(@PathVariable Long personalRecordId, @PathVariable Long spotId, @PathVariable Long memoId, @PathVariable Long pictureId) throws Exception {
        return pictureRemoveService.removePicture(personalRecordId, spotId, memoId, pictureId);
    }

}