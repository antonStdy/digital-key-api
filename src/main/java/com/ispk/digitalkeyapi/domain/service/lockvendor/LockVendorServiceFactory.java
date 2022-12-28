package com.ispk.digitalkeyapi.domain.service.lockvendor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LockVendorServiceFactory {

    private final List<LockVendorService> lockVendorServices;

    public LockVendorService get(String vendorName) {
        return lockVendorServices.stream()
                .filter(lockVendorService -> lockVendorService.getVendorName().equals(vendorName))
                .findFirst()
                .orElseThrow();
    }
}
