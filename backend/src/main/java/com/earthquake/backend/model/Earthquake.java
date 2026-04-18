package com.earthquake.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "earthquake")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Earthquake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double magnitude;

    @Column(name = "mag_type")
    private String magType;

    private String place;

    private String title;

    private Long time;

    private Double latitude;

    private Double longitude;
}