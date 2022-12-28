package com.ispk.digitalkeyapi.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LockDto {
    @NotNull private String lockVendorId;
    @NotNull private String serialNumber;
    @NotNull private String secret;
}
