package com.vehicle_registiration.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "vehicles")
@SequenceGenerator(name = "base_seq", sequenceName = "vehicle_id_seq", allocationSize = 1)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Column(name = "company_id")
    private Long componyId;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "tag")
    private String tag;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "model_year", nullable = false)
    private Integer modelYear;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private Group group;

}
