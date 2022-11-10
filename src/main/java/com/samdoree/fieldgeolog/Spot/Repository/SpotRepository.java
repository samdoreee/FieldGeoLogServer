package com.samdoree.fieldgeolog.Spot.Repository;

import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
public interface SpotRepository extends JpaRepository<Spot, Long> {
}
