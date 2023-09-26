package com.samdoree.fieldgeolog.PersonalRecord.Repository;

import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Long> {

    List<PersonalRecord> findByRecordTitleContaining(String recordTitle);
//    List<PersonalRecord> findByNicknameContaining(String nickname);
}