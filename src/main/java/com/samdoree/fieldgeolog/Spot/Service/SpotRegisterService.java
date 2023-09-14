package com.samdoree.fieldgeolog.Spot.Service;

import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Spot.DTO.SpotInsertRequestDTO;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import com.samdoree.fieldgeolog.Spot.DTO.SpotResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotRegisterService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;
    private final WeatherApi weatherApi;

    @Transactional
    public SpotResponseDTO addSpot(Long personalRecordId, SpotInsertRequestDTO spotInsertRequestDTO) throws Exception {

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());

        Spot spot = spotRepository.save(Spot.createFrom(personalRecord, spotInsertRequestDTO, weatherApi));
        return SpotResponseDTO.from(spot);
    }
}
