package com.samdoree.fieldgeolog.Spot.Service;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Memo.Repository.MemoRepository;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
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
public class SpotRemoveService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;

    @Transactional
    public boolean removeSpot(Long personalRecordId, Long spotId) {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
        Spot validSpot = spotRepository.findById(spotId)
                .filter(spot -> spot.isValid())
                .orElseThrow(() -> new NoSuchElementException("Spot not found or is not valid."));

        // Spot와 1:N 연관관계를 맺는 Memo 객체의 isValid 속성을 모두 false로 설정
        if (memoRepository.existsById(spotId)) {
            List<Memo> memoList = memoRepository.findAllBySpotId(spotId)
                    .stream()
                    .filter(memo -> memo.isValid())
                    .collect(Collectors.toList());

            for (Memo memo : memoList) {
                memo.markAsInvalid();
                memoRepository.save(memo);
            }
        }

        validSpot.markAsInvalid();
        spotRepository.save(validSpot);
        return true;
    }
}
