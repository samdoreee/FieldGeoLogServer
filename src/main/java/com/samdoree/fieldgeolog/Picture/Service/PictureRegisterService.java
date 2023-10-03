package com.samdoree.fieldgeolog.Picture.Service;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Memo.Repository.MemoRepository;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Picture.DTO.PictureRequestDTO;
import com.samdoree.fieldgeolog.Picture.DTO.PictureResponseDTO;
import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.Picture.Repository.PictureRepository;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import com.samdoree.fieldgeolog.Thumbnail.Entity.Thumbnail;
import com.samdoree.fieldgeolog.Thumbnail.Entity.ThumbnailType;
import com.samdoree.fieldgeolog.Thumbnail.Repository.ThumbnailRepository;
import com.samdoree.fieldgeolog.Thumbnail.Service.ThumbnailRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PictureRegisterService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;
    private final PictureRepository pictureRepository;
    private final ThumbnailRepository thumbnailRepository;
    private final ThumbnailRegisterService thumbnailRegisterService;

    @Transactional
    public PictureResponseDTO addPicture(Long personalRecordId, Long spotId, Long memoId, PictureRequestDTO pictureRequestDTO) throws Exception {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
        Spot validSpot = spotRepository.findById(spotId)
                .filter(spot -> spot.isValid())
                .orElseThrow(() -> new NoSuchElementException("Spot not found or is not valid."));
        Memo validMemo = memoRepository.findById(memoId)
                .filter(memo -> memo.isValid())
                .orElseThrow(() -> new NoSuchElementException("Memo not found or is not valid."));

        /*
         * Case 1) 썸네일 Thumbnail 아닌 경우 => 패스(썸네일로 등록할 필요없음!)
         * Case 2) 썸네일 Thumbnail인 경우 => 썸네일로 등록해야 함!
         *      => PersonalRecord Thumbnail인 경우
         *          확인:
         *          처리방법: 기존 기본 이미지 thumbnail 삭제 후, 새로운 thumbnail 등록
         *      => Spot Thumbnail인 경우
         *          확인:
         *          처리방법: 기존 기본 이미지 thumbnail 삭제 후, 새로운 thumbnail 등록
         * */

        // Case 1) Picture에 대한 Thumbnail 생성 및 연결
        Picture picture = Picture.createFrom(validMemo, pictureRequestDTO);

        // Case 2-1) PersonalRecord에 대한 기존 Thumbnail 제거 및 생성 및 연결
        if (validPersonalRecord.getThumbnail().getThumbnailType() == ThumbnailType.None) {
            Thumbnail exsitingThumbnail = validPersonalRecord.getThumbnail();
            exsitingThumbnail.markAsInvalid();
            thumbnailRepository.save(exsitingThumbnail);

            Thumbnail newThumbnail = thumbnailRegisterService.addPersonalRecordThumbnail(validPersonalRecord, picture);
            validPersonalRecord.setFileName(newThumbnail.getFileName());
        }

        // Case 2-2) Spot에 대한 기존 Thumbnail 제거 및 생성 및 연결
        if (validSpot.getThumbnail().getThumbnailType() == ThumbnailType.None) {
            Thumbnail exsitingThumbnail = validSpot.getThumbnail();
            exsitingThumbnail.markAsInvalid();
            thumbnailRepository.save(exsitingThumbnail);

            Thumbnail newThumbnail = thumbnailRegisterService.addSpotThumbnail(validSpot, picture);
            validSpot.setFileName(newThumbnail.getFileName());
        }

        pictureRepository.save(picture);
        return PictureResponseDTO.from(picture);
    }
}