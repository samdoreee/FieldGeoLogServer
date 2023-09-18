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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotSearchService {

    private final PersonalRecordRepository personalRecordRepository;
    private final SpotRepository spotRepository;

    public List<SpotResponseDTO> getAllSpotList(Long personalRecordId) {

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());

        List<Spot> spots = spotRepository.findAllByPersonalRecordId(personalRecordId);
        return spots.stream().map(SpotResponseDTO::new).collect(Collectors.toList());
    }

    public SpotResponseDTO getOneSpot(Long personalRecordId, Long spotId) {

        PersonalRecord personalRecord = personalRecordRepository.findById(personalRecordId)
                .orElseThrow(() -> new NullPointerException());
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new NullPointerException());

        return SpotResponseDTO.from(spot);
    }
}
