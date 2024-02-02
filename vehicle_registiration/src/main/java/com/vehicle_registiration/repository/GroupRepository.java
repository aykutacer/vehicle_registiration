package com.vehicle_registiration.repository;

import com.vehicle_registiration.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByParentGroupId(Long parentGroupId);
    Optional<Group> findByIdAndCompanyId(Long id, Long companyId);
    Group findByCompanyId(Long companyID);

}
