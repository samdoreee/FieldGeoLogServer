package com.samdoree.fieldgeolog.GeoPoint.Service;

import com.samdoree.fieldgeolog.GeoPoint.Repository.GeoPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeoPointRemoveService {

    private final GeoPointRepository geoPointRepository;

    @Transactional
    public boolean removeGeoPoint(Long geoPointId) {

        if (!geoPointRepository.existsById(geoPointId)) {
            return false;
        }

        geoPointRepository.deleteById(geoPointId);
        return true;
    }
}
