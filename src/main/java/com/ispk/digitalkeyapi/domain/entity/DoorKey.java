package com.ispk.digitalkeyapi.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "door_key")
@Getter
@Setter
public class DoorKey {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "duration", nullable = false)
    private Long duration;

    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    private DoorLock lock;

    @OneToMany
    private List<SharingUrl> sharingUrls = new ArrayList<>();
}
