package com.ispk.digitalkeyapi.domain.service.lockvendor;

import com.ispk.digitalkeyapi.domain.entity.DoorLock;

public interface LockVendorService {

    String getVendorName();

    DoorLock getLock(String serialNumber, String secret);
}
