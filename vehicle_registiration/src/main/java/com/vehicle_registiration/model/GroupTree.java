package com.vehicle_registiration.model;

import com.vehicle_registiration.entity.Group;
import com.vehicle_registiration.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupTree {
    private Group group;
    private List<Vehicle> vehicles;
    private List<GroupTree> subGroups;


    public GroupTree(Group group) {
        this.group = group;
        this.vehicles = new ArrayList<>();
        this.subGroups = new ArrayList<>();
    }

}
