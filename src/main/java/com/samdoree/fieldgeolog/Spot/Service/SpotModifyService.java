package com.samdoree.fieldgeolog.Spot.Service;

import com.samdoree.fieldgeolog.Spot.DTO.SpotEditRequestDTO;
import com.samdoree.fieldgeolog.Spot.DTO.SpotResponseDTO;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotModifyService {

    private final SpotRepository spotRepository;

    @Transactional
    public SpotResponseDTO modifySpot(Long spotId, SpotEditRequestDTO spotEditRequestDTO) throws Exception {

        if (!spotRepository.existsById(spotId)) {
            System.out.println("해당하는 spot을 찾을 수 없습니다");
            return null;
        }

        Spot spot = spotRepository.findById(spotId).get();
        spot.modifySpot(spotEditRequestDTO);
        return SpotResponseDTO.from(spot);
    }
}
