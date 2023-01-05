package com.ispk.digitalkeyapi.domain.entity;

import com.ispk.digitalkeyapi.domain.enumeration.LockStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "door_lock")
@Getter
@Setter
public class DoorLock {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "secret", nullable = false)
    private String secret;

    @Column(name = "lock_status", nullable = false)
    private LockStatus lockStatus;

    @ManyToOne
    @JoinColumn(name = "lock_vendor_id", nullable = false)
    private LockVendor lockVendor;
}
