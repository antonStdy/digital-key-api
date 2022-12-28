package com.ispk.digitalkeyapi.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lock_owner")
@Getter
@Setter
public class LockOwner {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Account account;

    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "lock_owner_doorLocks",
//            joinColumns = @JoinColumn(name = "lockOwner_id", referencedColumnName = "doorLocks_id"))
    private List<DoorLock> doorLocks = new ArrayList<>();
}
