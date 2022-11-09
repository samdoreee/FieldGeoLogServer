package com.samdoree.fieldgeolog.GeoPoint.Service;

import com.samdoree.fieldgeolog.GeoPoint.Entity.GeoPoint;
import com.samdoree.fieldgeolog.GeoPoint.Repository.GeoPointRepository;
import com.samdoree.fieldgeolog.GeoPoint.DTO.GeoPointResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeoPointSearchService {

    private final GeoPointRepository geoPointRepository;

    public List<GeoPointResponseDTO> findGeoPoints() {
        List<GeoPoint> geoPoints = geoPointRepository.findAll();
        return geoPoints.stream().map(GeoPointResponseDTO::new).collect(Collectors.toList());
    }
}
