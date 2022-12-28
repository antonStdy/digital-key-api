package com.ispk.digitalkeyapi.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lock_vendor")
@Getter
@Setter
public class LockVendor {
    @Id
    @GeneratedValue
    private Long id;

    private String vendorName;

    private String contactNumber;

    @OneToMany(mappedBy = "lockVendor")
    private List<DoorLock> locks = new ArrayList<>();
}
