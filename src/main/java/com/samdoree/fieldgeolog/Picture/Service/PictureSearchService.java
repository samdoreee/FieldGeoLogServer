package com.samdoree.fieldgeolog.Picture.Service;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Memo.Repository.MemoRepository;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Picture.DTO.PictureResponseDTO;
import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.Picture.Repository.PictureRepository;
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
public class PictureSearchService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;
    private final PictureRepository pictureRepository;

    public List<PictureResponseDTO> getAllPictureList(Long personalRecordId, Long spotId, Long memoId) {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.getIsValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
        Spot validSpot = spotRepository.findById(spotId)
                .filter(spot -> spot.getIsValid())
                .orElseThrow(() -> new NoSuchElementException("Spot not found or is not valid."));
        Memo validMemo = memoRepository.findById(memoId)
                .filter(memo -> memo.getIsValid())
                .orElseThrow(() -> new NoSuchElementException("Memo not found or is not valid."));

        List<Picture> pictures = pictureRepository.findAllByMemoId(memoId);
        return pictures.stream().map(PictureResponseDTO::new).collect(Collectors.toList());
    }

    public PictureResponseDTO getOnePicture(Long personalRecordId, Long spotId, Long memoId, Long pictureId) {

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

        return PictureResponseDTO.from(validPicture);
    }
}