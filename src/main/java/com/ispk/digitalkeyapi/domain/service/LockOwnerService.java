package com.ispk.digitalkeyapi.domain.service;

import com.ispk.digitalkeyapi.domain.dto.AddKeyDto;
import com.ispk.digitalkeyapi.domain.dto.AddLockDto;
import com.ispk.digitalkeyapi.domain.dto.SharedKeyUrlDto;
import com.ispk.digitalkeyapi.domain.entity.DoorKey;
import com.ispk.digitalkeyapi.domain.entity.LockOwner;
import com.ispk.digitalkeyapi.domain.entity.SharingUrl;
import com.ispk.digitalkeyapi.domain.repository.LockOwnerRepository;
import com.ispk.digitalkeyapi.domain.service.lockvendor.LockVendorServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LockOwnerService {

    @Value("${key-sharing-api.shareKey}")
    private String keySharingEndpoint;

    private final LockOwnerRepository lockOwnerRepository;
    private final LockVendorServiceFactory lockVendorServiceFactory;

    public LockOwner getByEmail(String email) {
        return lockOwnerRepository.findByAccount_Email(email)
                .orElseThrow();
    }

    public void addLock(LockOwner lockOwner, AddLockDto addLockDto) {
        var lockVendorService = lockVendorServiceFactory
                .get(addLockDto.getLockVendorId());

        var lock = lockVendorService
                .getLock(addLockDto.getSerialNumber(), addLockDto.getSecret());

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

    public void createKey(LockOwner lockOwner, Long lockId, AddKeyDto addKeyDto) {
        var isNameDuplicated = lockOwner.getDoorKeys()
                .stream().anyMatch(it -> it.getName().equals(addKeyDto.getName()));

        if (isNameDuplicated) {
            throw new IllegalArgumentException("Name already exists");
        }

        var lock = lockOwner.getDoorLocks().stream()
                .filter(it -> it.getId().equals(lockId))
                .findFirst()
                .orElseThrow();

        var key = new DoorKey();
        key.setName(addKeyDto.getName());
        key.setDuration(addKeyDto.getDuration());
        key.setLock(lock);

        lockOwner.getDoorKeys().add(key);

        lockOwnerRepository.save(lockOwner);
    }

    public void removeKey(LockOwner lockOwner, Long keyId) {
        var key = getKey(lockOwner, keyId);

        lockOwner.getDoorKeys().remove(key);

        lockOwnerRepository.save(lockOwner);
    }

    public SharedKeyUrlDto createKeyShareUrl(LockOwner lockOwner, Long keyId) {
        var key = getKey(lockOwner, keyId);
        var urlId = UUID.randomUUID().toString();
        var sharingUrl = new SharingUrl();
        sharingUrl.setId(urlId);
        sharingUrl.setUsed(false);

        key.getSharingUrls().add(sharingUrl);

        return new SharedKeyUrlDto(sharingUrl + urlId);
    }

    private DoorKey getKey(LockOwner lockOwner, Long keyId) {
        return lockOwner.getDoorKeys()
                .stream().filter(it -> it.getId().equals(keyId))
                .findFirst()
                .orElseThrow();
    }
}
