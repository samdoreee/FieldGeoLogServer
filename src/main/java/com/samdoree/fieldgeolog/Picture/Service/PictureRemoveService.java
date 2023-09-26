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

import java.util.NoSuchElementException;


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

        validPicture.markAsInvalid();
        pictureRepository.save(validPicture);
        return true;
    }
}
