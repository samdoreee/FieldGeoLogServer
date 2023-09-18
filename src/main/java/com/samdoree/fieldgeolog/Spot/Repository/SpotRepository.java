package com.samdoree.fieldgeolog.Spot.Repository;

import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;


@EnableJpaAuditing
public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findAllByPersonalRecordId(Long personalRecordId);
}
