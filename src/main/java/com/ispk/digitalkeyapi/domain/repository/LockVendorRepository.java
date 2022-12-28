package com.ispk.digitalkeyapi.domain.repository;

import com.ispk.digitalkeyapi.domain.entity.LockVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LockVendorRepository extends JpaRepository<LockVendor, Long> {

    Optional<LockVendor> findByVendorName(String vendorName);
}
