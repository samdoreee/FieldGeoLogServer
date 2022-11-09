package com.samdoree.fieldgeolog.GeoPoint.Repository;

import com.samdoree.fieldgeolog.GeoPoint.Entity.GeoPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
public interface GeoPointRepository extends JpaRepository<GeoPoint, Long> {
    List<GeoPoint> findAll();
}
