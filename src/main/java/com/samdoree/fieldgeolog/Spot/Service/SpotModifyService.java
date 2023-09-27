package com.samdoree.fieldgeolog.Spot.Service;

import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Spot.DTO.SpotEditRequestDTO;
import com.samdoree.fieldgeolog.Spot.DTO.SpotResponseDTO;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotModifyService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;

    @Transactional
    public SpotResponseDTO modifySpot(Long personalRecordId, Long spotId, SpotEditRequestDTO spotEditRequestDTO) throws Exception {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
        Spot validSpot = spotRepository.findById(spotId)
                .filter(spot -> spot.isValid())
                .orElseThrow(() -> new NoSuchElementException("Spot not found or is not valid."));

        validSpot.modifySpot(spotEditRequestDTO);
        return SpotResponseDTO.from(validSpot);
    }
}
