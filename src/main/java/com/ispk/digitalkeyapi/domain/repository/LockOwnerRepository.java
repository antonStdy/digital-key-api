package com.ispk.digitalkeyapi.domain.repository;

import com.ispk.digitalkeyapi.domain.entity.LockOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LockOwnerRepository extends JpaRepository<LockOwner, Long> {

    Optional<LockOwner> findByAccount_EmailAndAccount_Password(
            String email, String password);

    Optional<LockOwner> findByAccount_Email(
            String email);

    boolean existsByAccount_Email(String email);
}
