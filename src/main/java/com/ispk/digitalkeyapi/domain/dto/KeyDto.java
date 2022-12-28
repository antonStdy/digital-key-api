package com.ispk.digitalkeyapi.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KeyDto {
    @NotNull private String name;
    private Long duration;
}
