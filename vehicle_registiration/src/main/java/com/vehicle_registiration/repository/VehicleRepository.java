package com.vehicle_registiration.repository;

import com.vehicle_registiration.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByComponyId(Long compony_id);

    Optional<Vehicle> findByIdAndAndComponyId(Long id, Long compony_id);

    List<Vehicle> findByGroupIdAndComponyIdAndIdIn(Long groupId, Long compony_id, List<Long> vehicleIds);

    List<Vehicle> findByIdInAndAndComponyId(List<Long> id, Long compony_id);

}
