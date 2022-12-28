package com.ispk.digitalkeyapi.domain.service.lockvendor;

import com.ispk.digitalkeyapi.domain.entity.DoorLock;
import com.ispk.digitalkeyapi.domain.entity.LockVendor;
import com.ispk.digitalkeyapi.domain.enumeration.LockStatus;
import com.ispk.digitalkeyapi.domain.repository.LockVendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StubLockVendorService implements LockVendorService {

    private final LockVendorRepository lockVendorRepository;

    @Override
    public String getVendorName() {
        return "Stub vendor";
    }

    @Override
    public DoorLock getLock(String serialNumber, String secret) {
        var vendor = lockVendorRepository.findByVendorName(getVendorName())
                .orElseGet(this::createVendorStub);

        var lockOpt = vendor.getLocks().stream()
                .filter(it -> it.getSerialNumber().equals(serialNumber))
                .findFirst();
        if (lockOpt.isPresent()) {
            return lockOpt.get();
        }

        var lock = new DoorLock();
        lock.setLockVendor(vendor);
        lock.setSerialNumber(serialNumber);
        lock.setSecret(secret);
        lock.setLockStatus(LockStatus.CLOSED);

        vendor.getLocks().add(lock);

        lockVendorRepository.save(vendor);

        return lock;
    }

    private LockVendor createVendorStub() {
        var vendor = new LockVendor();
        vendor.setVendorName(getVendorName());
        vendor.setContactNumber("+21343253453");
        return lockVendorRepository.save(vendor);
    }
}
