package com.ispk.digitalkeyapi.domain.service.lockvendor;

import com.ispk.digitalkeyapi.domain.entity.DoorLock;
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
                .orElseThrow();

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

        vendor.getLocks().add(lock);

        lockVendorRepository.save(vendor);

        return lock;
    }
}
