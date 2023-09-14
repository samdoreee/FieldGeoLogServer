package com.samdoree.fieldgeolog.Spot.Repository;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;


@EnableJpaAuditing
public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findAllByPersonalRecordId(Long personalRecordId);
//    Optional<Spot> findByPersonalRecordIdAndSpotId(Long personalRecordId, Long spotId);
}
