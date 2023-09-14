package com.samdoree.fieldgeolog.Memo.Service;

import com.samdoree.fieldgeolog.File.Entity.File;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoRegisterService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;
    private final String fileDir = "src/main/resources/files/";

    @Transactional
    public MemoResponseDTO addMemo(Long personalRecordId, Long spotId, MemoRequestDTO memoRequestDTO, MultipartFile[] multipartFiles) throws Exception {

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new NullPointerException());

        Memo memo = Memo.createFrom(spot, memoRequestDTO);

        if (multipartFiles != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                memo.addFile(File.createFile(multipartFile, fileDir, spotId, memo.getId()));
            }
        }

        return MemoResponseDTO.from(memoRepository.save(memo));
    }
}
