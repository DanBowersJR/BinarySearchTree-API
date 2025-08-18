package com.keyin.binarysearchtreeapi.repository;

import com.keyin.binarysearchtreeapi.model.TreeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TreeRecordRepository extends JpaRepository<TreeRecord, Long> {

    // 🔹 Find a record by its value
    Optional<TreeRecord> findByValue(int value);

    // 🔹 Delete a record directly by its value
    void deleteByValue(int value);
}
