package com.samdoree.fieldgeolog.PersonalRecord.Repository;

import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Long> {

}