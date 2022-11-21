package com.samdoree.fieldgeolog.Spot.Service;

import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import com.samdoree.fieldgeolog.Spot.DTO.SpotRequestDTO;
import com.samdoree.fieldgeolog.Spot.DTO.SpotResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotRegisterService {

    private final SpotRepository spotRepository;

    @Transactional
    public SpotResponseDTO addSpot(SpotRequestDTO spotRequestDTO) {
        Spot spot = spotRepository.save(Spot.createFrom(spotRequestDTO));
        return SpotResponseDTO.from(spot);
    }
}
