package com.ispk.digitalkeyapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "door_lock")
@Getter
@Setter
public class DoorLock {
    @Id
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;
}
