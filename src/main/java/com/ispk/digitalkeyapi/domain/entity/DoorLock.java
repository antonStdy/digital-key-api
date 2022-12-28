package com.ispk.digitalkeyapi.domain.entity;

import jakarta.persistence.*;
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

    private String secret;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private LockVendor lockVendor;
}
