package com.samdoree.fieldgeolog.Spot.Service;

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

    private final SpotRepository spotRepository;

    public List<SpotResponseDTO> getAllSpotList() {
        List<Spot> spots = spotRepository.findAll();
        return spots.stream().map(SpotResponseDTO::new).collect(Collectors.toList());
    }

    public SpotResponseDTO getOneSpot(Long spotId) {

        if (!spotRepository.existsById(spotId)) {
            System.out.println("해당하는 spot을 찾을 수 없습니다");
            return null;
        }

        Spot spot = spotRepository.findById(spotId).get();
        return SpotResponseDTO.from(spot);
    }
}
