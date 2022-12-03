package com.samdoree.fieldgeolog.Spot.Service;

import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotRemoveService {

    private final SpotRepository spotRepository;

    @Transactional
    public boolean removeSpot(Long SpotId) {

        if (!spotRepository.existsById(SpotId)) {
            System.out.println("해당하는 spot을 찾을 수 없습니다");
            return false;
        }

        spotRepository.deleteById(SpotId);
        return true;
    }
}
