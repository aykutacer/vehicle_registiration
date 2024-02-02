package com.vehicle_registiration.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "groups")
@SequenceGenerator(name = "base_seq", sequenceName = "groups_id_seq", allocationSize = 1)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "company_id")
    private Long companyId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parent_group_id")
    private Group parentGroup;

    @OneToMany(mappedBy = "parentGroup")
    @JsonIgnore
    private Set<Group> subGroups = new HashSet<>();

    @OneToMany(mappedBy = "group")
    private Set<Vehicle> vehicles = new HashSet<>();

}
