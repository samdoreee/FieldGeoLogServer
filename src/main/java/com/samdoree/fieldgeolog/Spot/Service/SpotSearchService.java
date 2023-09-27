package com.samdoree.fieldgeolog.Spot.Service;

import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.PersonalRecord.Repository.PersonalRecordRepository;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import com.samdoree.fieldgeolog.Spot.DTO.SpotResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotSearchService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;

    public List<SpotResponseDTO> getAllSpotList(Long personalRecordId) {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));

        List<Spot> validSpotList = spotRepository.findAllByPersonalRecordId(personalRecordId)
                .stream()
                .filter(spot -> spot.isValid())
                .collect(Collectors.toList());

        return validSpotList.stream()
                .map(SpotResponseDTO::new)
                .collect(Collectors.toList());
    }

    public SpotResponseDTO getOneSpot(Long personalRecordId, Long spotId) {

        PersonalRecord validPersonalRecord = personalRecordRepository.findById(personalRecordId)
                .filter(personalRecord -> personalRecord.isValid())
                .orElseThrow(() -> new NoSuchElementException("PersonalRecord not found or is not valid."));
        Spot validSpot = spotRepository.findById(spotId)
                .filter(spot -> spot.isValid())
                .orElseThrow(() -> new NoSuchElementException("Spot not found or is not valid."));

        return SpotResponseDTO.from(validSpot);
    }
}
