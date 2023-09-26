package com.samdoree.fieldgeolog.Picture.Service;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Memo.Repository.MemoRepository;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.Picture.Repository.PictureRepository;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PictureRemoveService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;
    private final PictureRepository pictureRepository;

    @Transactional
    public boolean removePicture(Long personalRecordId, Long spotId, Long memoId, Long pictureId) {

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new NullPointerException());
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new NullPointerException());
        Picture existingPicture = pictureRepository.findById(pictureId)
                .orElseThrow(() -> new NullPointerException());

        // Picture 객체의 isValid 값을 false로 설정
        Picture updatedPicture = Picture.builder()
                .isValid(false)  //변경할 값을 설정
                .build();

        // 이미 존재하는 객체를 변경된 객체로 대체
        existingPicture = updatedPicture;

//        pictureRepository.deleteById(pictureId);
        return true;
    }
}
