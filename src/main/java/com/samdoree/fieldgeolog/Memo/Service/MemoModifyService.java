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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoModifyService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;

    @Transactional
    public MemoResponseDTO modifyMemo(Long personalRecordId, Long spotId, Long memoId, MemoRequestDTO memoRequestDTO) {

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new NullPointerException());
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new NullPointerException());

        memo.modifyMemo(memoRequestDTO);
        return MemoResponseDTO.from(memo);
    }

}
