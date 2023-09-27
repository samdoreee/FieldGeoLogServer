package com.samdoree.fieldgeolog.Memo.Service;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.Picture.Repository.PictureRepository;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Memo.Repository.MemoRepository;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoRemoveService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;
    private final PictureRepository pictureRepository;

    @Transactional
    public boolean removeMemo(Long personalRecordId, Long spotId, Long memoId) {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
        Spot validSpot = spotRepository.findById(spotId)
                .filter(spot -> spot.isValid())
                .orElseThrow(() -> new NoSuchElementException("Spot not found or is not valid."));
        Memo validMemo = memoRepository.findById(memoId)
                .filter(memo -> memo.isValid())
                .orElseThrow(() -> new NoSuchElementException("Memo not found or is not valid."));

        // Memo와 1:N 연관관계를 맺는 Picture 객체의 isValid 속성을 모두 false로 설정
        List<Picture> pictureList = pictureRepository.findAllByMemoId(memoId)
                .stream()
                .filter(picture -> picture.isValid())
                .collect(Collectors.toList());

        for (Picture picture : pictureList) {
            picture.markAsInvalid();
            pictureRepository.save(picture);
        }

        validMemo.markAsInvalid();
        memoRepository.save(validMemo);
        return true;
    }
}
