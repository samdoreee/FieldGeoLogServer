package com.samdoree.fieldgeolog.Memo.Service;

import com.samdoree.fieldgeolog.Memo.DTO.MemoRequestDTO;
import com.samdoree.fieldgeolog.Memo.DTO.MemoResponseDTO;
import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Memo.Repository.MemoRepository;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoModifyService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;

    @Transactional
    public MemoResponseDTO modifyMemo(Long personalRecordId, Long spotId, Long memoId, MemoRequestDTO memoRequestDTO) {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.getIsValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
        Spot validSpot = spotRepository.findById(spotId)
                .filter(spot -> spot.getIsValid())
                .orElseThrow(() -> new NoSuchElementException("Spot not found or is not valid."));
        Memo validMemo = memoRepository.findById(memoId)
                .filter(memo -> memo.getIsValid())
                .orElseThrow(() -> new NoSuchElementException("Memo not found or is not valid."));

        validMemo.modifyMemo(memoRequestDTO);
        return MemoResponseDTO.from(validMemo);
    }

}
