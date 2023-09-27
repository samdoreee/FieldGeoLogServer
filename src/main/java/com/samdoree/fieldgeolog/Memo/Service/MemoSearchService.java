package com.samdoree.fieldgeolog.Memo.Service;

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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoSearchService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;

    public List<MemoResponseDTO> getAllMemoList(Long personalRecordId, Long spotId) {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
        Spot validSpot = spotRepository.findById(spotId)
                .filter(spot -> spot.isValid())
                .orElseThrow(() -> new NoSuchElementException("Spot not found or is not valid."));

        List<Memo> validMemoList = memoRepository.findAllBySpotId(spotId)
                .stream()
                .filter(memo -> memo.isValid())
                .collect(Collectors.toList());

        return validMemoList.stream()
                .map(MemoResponseDTO::from)
                .collect(Collectors.toList());
    }

    public MemoResponseDTO getOneMemo(Long personalRecordId, Long spotId, Long memoId) {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
        Spot validSpot = spotRepository.findById(spotId)
                .filter(spot -> spot.isValid())
                .orElseThrow(() -> new NoSuchElementException("Spot not found or is not valid."));
        Memo validMemo = memoRepository.findById(memoId)
                .filter(memo -> memo.isValid())
                .orElseThrow(() -> new NoSuchElementException("Memo not found or is not valid."));

        return MemoResponseDTO.from(validMemo);
    }
}
