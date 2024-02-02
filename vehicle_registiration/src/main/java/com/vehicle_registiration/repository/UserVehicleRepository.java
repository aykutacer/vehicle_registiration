package com.vehicle_registiration.repository;

import com.vehicle_registiration.entity.UserVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserVehicleRepository extends JpaRepository<UserVehicle, Long> {
    List<UserVehicle> findByUserId(Long userId);


    List<UserVehicle> findAllByUserId(Long userId);

    @Query("SELECT uv.vehicleId FROM UserVehicle uv WHERE uv.userId = :userId")
    List<Long> findVehicleIdsByUserId(@Param("userId") Long userId);
}
