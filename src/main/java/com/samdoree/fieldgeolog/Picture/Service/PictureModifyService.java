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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PictureModifyService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;
    private final PictureRepository pictureRepository;

    @Transactional
    public PictureResponseDTO modifyPicture(Long personalRecordId, Long spotId, Long memoId, Long pictureId, PictureRequestDTO pictureRequestDTO) throws Exception {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.getIsValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
        Spot validSpot = spotRepository.findById(spotId)
                .filter(spot -> spot.getIsValid())
                .orElseThrow(() -> new NoSuchElementException("Spot not found or is not valid."));
        Memo validMemo = memoRepository.findById(memoId)
                .filter(memo -> memo.getIsValid())
                .orElseThrow(() -> new NoSuchElementException("Memo not found or is not valid."));
        Picture validPicture = pictureRepository.findById(pictureId)
                .filter(picture -> picture.getIsValid())
                .orElseThrow(() -> new NoSuchElementException("Picture not found or is not valid."));

        validPicture.modifyPicture(pictureRequestDTO);
        return PictureResponseDTO.from(validPicture);
    }
}
