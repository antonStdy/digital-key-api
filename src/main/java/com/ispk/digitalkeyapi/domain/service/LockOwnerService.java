package com.ispk.digitalkeyapi.domain.service;

import com.ispk.digitalkeyapi.domain.dto.LockDto;
import com.ispk.digitalkeyapi.domain.entity.LockOwner;
import com.ispk.digitalkeyapi.domain.repository.LockOwnerRepository;
import com.ispk.digitalkeyapi.domain.service.lockvendor.LockVendorServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LockOwnerService {

    private final LockOwnerRepository lockOwnerRepository;
    private final LockVendorServiceFactory lockVendorServiceFactory;

    public LockOwner getByEmail(String email) {
        return lockOwnerRepository.findByAccount_Email(email)
                .orElseThrow();
    }

    public void addLock(LockOwner lockOwner, LockDto lockDto) {
        var lockVendorService = lockVendorServiceFactory
                .get(lockDto.getLockVendorId());

        var lock = lockVendorService
                .getLock(lockDto.getSerialNumber(), lockDto.getSecret());

        lockOwner.getDoorLocks().add(lock);

        lockOwnerRepository.save(lockOwner);
    }
}
