package com.ispk.digitalkeyapi.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sharing_url")
@Getter
@Setter
public class SharingUrl {
    @Id
    private String id;

    private boolean isUsed;
}
