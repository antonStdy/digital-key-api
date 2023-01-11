package com.ispk.digitalkeyapi.domain.dto;

import com.ispk.digitalkeyapi.domain.enumeration.LockStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetLockDto {
    private Long id;

    private String serialNumber;

    private String secret;

    private LockStatus lockStatus;
}
