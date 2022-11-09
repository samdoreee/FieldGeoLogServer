package com.samdoree.fieldgeolog.GeoPoint.Service;

import com.samdoree.fieldgeolog.GeoPoint.Entity.GeoPoint;
import com.samdoree.fieldgeolog.GeoPoint.Repository.GeoPointRepository;
import com.samdoree.fieldgeolog.GeoPoint.DTO.GeoPointRequestDTO;
import com.samdoree.fieldgeolog.GeoPoint.DTO.GeoPointResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeoPointRegisterService {

    private final GeoPointRepository geoPointRepository;

    @Transactional
    public GeoPointResponseDTO addGeoPoint(GeoPointRequestDTO geoPointRequestDTO) {
        GeoPoint geoPoint = geoPointRepository.save(GeoPoint.createGeoPoint(geoPointRequestDTO));
        return GeoPointResponseDTO.from(geoPoint);
    }
}
