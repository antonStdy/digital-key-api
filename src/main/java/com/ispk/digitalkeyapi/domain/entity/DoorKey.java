package com.ispk.digitalkeyapi.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Entity
@Table(name = "door_key")
@Getter
@Setter
public class DoorKey {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long duration;

    @OneToOne
    private DoorLock lock;
}
