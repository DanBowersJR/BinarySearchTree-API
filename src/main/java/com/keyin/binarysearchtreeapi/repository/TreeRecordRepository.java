package com.keyin.binarysearchtreeapi.repository;

import com.keyin.binarysearchtreeapi.model.TreeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TreeRecordRepository extends JpaRepository<TreeRecord, Long> {

    // Get the most recently saved tree record
    Optional<TreeRecord> findTopByOrderByIdDesc();
}
