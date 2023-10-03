package com.samdoree.fieldgeolog.Picture.Service;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Memo.Repository.MemoRepository;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
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

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PictureRemoveService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;
    private final PictureRepository pictureRepository;
    private final ThumbnailRepository thumbnailRepository;
    private final ThumbnailRegisterService thumbnailRegisterService;

    @Transactional
    public boolean removePicture(Long personalRecordId, Long spotId, Long memoId, Long pictureId) throws Exception {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
        Spot validSpot = spotRepository.findById(spotId)
                .filter(spot -> spot.isValid())
                .orElseThrow(() -> new NoSuchElementException("Spot not found or is not valid."));
        Memo validMemo = memoRepository.findById(memoId)
                .filter(memo -> memo.isValid())
                .orElseThrow(() -> new NoSuchElementException("Memo not found or is not valid."));
        Picture validPicture = pictureRepository.findById(pictureId)
                .filter(picture -> picture.isValid())
                .orElseThrow(() -> new NoSuchElementException("Picture not found or is not valid."));

        /*
         * Case 1) 썸네일 Thumbnail이 아닌 경우
         * Case 2) 썸네일 Thumbnail인 경우
         *      2-1) => PersonalRecord Thumbnail인 경우
         *      2-2) => Spot Thumbnail인 경우
         *      2-3) => PersonalRecord + Spot 모두의 Thumbnail인 경우
         * */
        List<Thumbnail> existingThumbnailList = thumbnailRepository.findByPictureId(pictureId);

        // Case 2) 해당 picture가 썸네일사진인 경우
        validPicture.markAsInvalid();

        for (Thumbnail existingThumbnail : existingThumbnailList) {
            ThumbnailType type = existingThumbnail.getThumbnailType();

            // 해당 thumbnail이 PersonalRecord의 썸네일인 경우
            if (type == ThumbnailType.PERSONAL_RECORD) {

                // 기존 썸네일 삭제
                existingThumbnail.markAsInvalid();
                thumbnailRepository.save(existingThumbnail);

                // PersonalRecord의 썸네일 업데이트
                Thumbnail newThumbnail = updatePersonalRecordThumbnail(validPersonalRecord);
                personalRecordRepository.save(validPersonalRecord);
            }
            // 해당 thumbnail이 Spot의 썸네일인 경우
            else if (type == ThumbnailType.SPOT) {

                // 기존 썸네일 삭제
                existingThumbnail.markAsInvalid();
                thumbnailRepository.save(existingThumbnail);

                // Spot의 썸네일 업데이트
                Thumbnail newThumbnail = updateSpotThumbnail(validSpot);
                spotRepository.save(validSpot);
            }
        }

        validPicture.markAsInvalid();
        pictureRepository.save(validPicture);
        return true;
    }


    // 썸네일 사진 update하기
    private Thumbnail updatePersonalRecordThumbnail(PersonalRecord personalRecord) throws Exception {

        List<Spot> spots = personalRecord.getSpotList();
        if (!spots.isEmpty()) {
            for (Spot spot : spots) {
                List<Memo> memos = spot.getMemoList();
                if (!memos.isEmpty()) {
                    for (Memo memo : memos) {
                        List<Picture> pictures = memo.getPictureList();
                        if (!pictures.isEmpty()) {
                            for (Picture picture : pictures) {
                                if (picture.isValid()) {
                                    // 유효한 Picture를 찾으면 새로운 썸네일로 설정
                                    Thumbnail newThumbnail = thumbnailRegisterService.addPersonalRecordThumbnail(personalRecord, picture);
                                    personalRecord.setThumbnailPath(newThumbnail.getFilePath());
                                    thumbnailRepository.save(newThumbnail);
                                    return newThumbnail; // 썸네일 설정이 완료되었으므로 반복 종료
                                }
                            }
                        }
                    }
                }
            }
        }
        Thumbnail newThumbnail = thumbnailRegisterService.addPersonalRecordThumbnail(personalRecord, null);
        personalRecord.setThumbnailPath(newThumbnail.getFilePath());
        thumbnailRepository.save(newThumbnail);
        return newThumbnail;
    }

    private Thumbnail updateSpotThumbnail(Spot spot) throws Exception {

        List<Memo> memos = spot.getMemoList();
        if (!memos.isEmpty()) {
            for (Memo memo : memos) {
                List<Picture> pictures = memo.getPictureList();
                if (!pictures.isEmpty()) {
                    for (Picture picture : pictures) {
                        if (picture.isValid()) {
                            // 유효한 Picture를 찾으면 새로운 썸네일로 설정
                            Thumbnail newThumbnail = thumbnailRegisterService.addSpotThumbnail(spot, picture);
                            spot.setThumbnailPath(newThumbnail.getFilePath());
                            thumbnailRepository.save(newThumbnail);
                            return newThumbnail; // 썸네일 설정이 완료되었으므로 반복 종료
                        }
                    }
                }
            }
        }
        Thumbnail newThumbnail = thumbnailRegisterService.addSpotThumbnail(spot, null);
        spot.setThumbnailPath(newThumbnail.getFilePath());
        thumbnailRepository.save(newThumbnail);
        return newThumbnail;
    }
}
