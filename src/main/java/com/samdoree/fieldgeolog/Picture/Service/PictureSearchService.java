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

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new NullPointerException());
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new NullPointerException());

        List<Picture> pictures = pictureRepository.findAllByMemoId(memoId);
        return pictures.stream().map(PictureResponseDTO::new).collect(Collectors.toList());
    }

    public PictureResponseDTO getOnePicture(Long personalRecordId, Long spotId, Long memoId, Long pictureId) {

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new NullPointerException());
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new NullPointerException());
        Picture picture = pictureRepository.findById(pictureId)
                .orElseThrow(() -> new NullPointerException());

        return PictureResponseDTO.from(picture);
    }
}