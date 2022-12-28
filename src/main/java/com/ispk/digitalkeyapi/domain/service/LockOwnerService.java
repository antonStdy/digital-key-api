package com.ispk.digitalkeyapi.domain.service;

import com.ispk.digitalkeyapi.domain.dto.KeyDto;
import com.ispk.digitalkeyapi.domain.dto.LockDto;
import com.ispk.digitalkeyapi.domain.entity.DoorKey;
import com.ispk.digitalkeyapi.domain.entity.LockOwner;
import com.ispk.digitalkeyapi.domain.repository.LockOwnerRepository;
import com.ispk.digitalkeyapi.domain.service.lockvendor.LockVendorServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void disconnectLock(LockOwner lockOwner, Long lockId) {
        var locks = lockOwner.getDoorLocks();

        var lock = locks.stream().filter(it -> it.getId().equals(lockId))
                .findFirst()
                .orElseThrow();

        locks.remove(lock);

        lockOwnerRepository.save(lockOwner);
    }

    public void createKey(LockOwner lockOwner, Long lockId, KeyDto keyDto) {
        var isNameDuplicated = lockOwner.getDoorKeys()
                .stream().anyMatch(it -> it.getName().equals(keyDto.getName()));

        if (isNameDuplicated) {
            throw new IllegalArgumentException("Name already exists");
        }

        var lock = lockOwner.getDoorLocks().stream()
                .filter(it -> it.getId().equals(lockId))
                .findFirst()
                .orElseThrow();

        var key = new DoorKey();
        key.setName(key.getName());
        key.setDuration(keyDto.getDuration());
        key.setLock(lock);

        lockOwner.getDoorKeys().add(key);

        lockOwnerRepository.save(lockOwner);
    }
}
