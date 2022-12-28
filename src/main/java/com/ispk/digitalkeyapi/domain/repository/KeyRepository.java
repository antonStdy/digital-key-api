package com.ispk.digitalkeyapi.domain.repository;

import com.ispk.digitalkeyapi.domain.entity.DoorKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyRepository extends JpaRepository<DoorKey, Long> {
}
