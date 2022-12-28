package com.ispk.digitalkeyapi.domain.dto;

import lombok.Data;

@Data
public class LockDto {
    private String lockVendorId;
    private String serialNumber;
    private String secret;
}
