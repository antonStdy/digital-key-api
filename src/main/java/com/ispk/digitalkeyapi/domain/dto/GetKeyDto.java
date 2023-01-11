package com.ispk.digitalkeyapi.domain.dto;

import com.ispk.digitalkeyapi.domain.entity.SharingUrl;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetKeyDto {
    private Long id;

    private String name;

    private Long duration;

    private List<SharingUrl> sharingUrls;
}
