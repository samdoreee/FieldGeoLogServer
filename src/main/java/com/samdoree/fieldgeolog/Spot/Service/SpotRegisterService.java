package com.samdoree.fieldgeolog.Spot.Service;

import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Spot.DTO.SpotInsertRequestDTO;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import com.samdoree.fieldgeolog.Spot.DTO.SpotResponseDTO;
import com.samdoree.fieldgeolog.Thumbnail.Entity.Thumbnail;
import com.samdoree.fieldgeolog.Thumbnail.Service.ThumbnailRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotRegisterService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final WeatherApi weatherApi;
    private final ThumbnailRegisterService thumbnailRegisterService;

    @Transactional
    public SpotResponseDTO addSpot(Long personalRecordId, SpotInsertRequestDTO spotInsertRequestDTO) throws Exception {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));

        Spot spot = Spot.createFrom(validPersonalRecord, spotInsertRequestDTO, weatherApi);

        // Spot에 대한 Tumbnail 생성 및 연결
        Thumbnail thumbnail = thumbnailRegisterService.addSpotThumbnail(spot, null);
        spot.setThumbnailPath(thumbnail.getFilePath());

        // Spot 저장
        spotRepository.save(spot);
        return SpotResponseDTO.from(spot);
    }
}
