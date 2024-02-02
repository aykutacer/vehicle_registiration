package com.vehicle_registiration.service;

import com.vehicle_registiration.entity.Group;
import com.vehicle_registiration.entity.UserVehicle;
import com.vehicle_registiration.entity.Vehicle;
import com.vehicle_registiration.model.GroupTree;
import com.vehicle_registiration.repository.GroupRepository;
import com.vehicle_registiration.repository.UserVehicleRepository;
import com.vehicle_registiration.repository.VehicleRepository;
import com.vehicle_registiration.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserVehicleRepository userVehicleRepository;

    public GroupTree getGroupTree(Long userId, Long groupId, Long companyId) throws Exception {
        // Kullanıcının yetkili olduğu grupları buluyoruz
        List<UserVehicle> userVehicleList = userVehicleRepository.findAllByUserId(userId);
        List<Long> authorizedGroupIds = new ArrayList<>();
        for (UserVehicle uv : userVehicleList
        ) {
            authorizedGroupIds.add(uv.getVehicleId());
        }

        // İstenen grup id sine göre ağacı oluşturuyoruz
        return buildGroupTree(groupId, authorizedGroupIds, companyId);
    }

    private GroupTree buildGroupTree(Long groupId, List<Long> authorizedGroupIds, Long companyId) throws Exception {
        Group group = groupRepository.findByIdAndCompanyId(groupId, companyId)
                .orElseThrow(() -> new Exception(Constants.NOT_AUTORIZED + groupId));

        Set<Vehicle> filteredVehicles = group.getVehicles().stream()
                .filter(vehicle -> vehicle.getComponyId().equals(companyId))
                .collect(Collectors.toSet());
        group.setVehicles(filteredVehicles);
        GroupTree groupTree = new GroupTree(group);


        // İlgili gruptaki yetkili araçları eklemek için
        List<Vehicle> vehicles = vehicleRepository.findByGroupIdAndComponyIdAndIdIn(groupId, companyId, authorizedGroupIds);
        groupTree.setVehicles(vehicles);

        // Alt grupları eklemek için
        List<Group> subGroups = groupRepository.findByParentGroupId(groupId);
        for (Group subGroup : subGroups) {
            GroupTree subGroupTree = buildGroupTree(subGroup.getId(), authorizedGroupIds, companyId);
            groupTree.getSubGroups().add(subGroupTree);
        }

        return groupTree;
    }
}
