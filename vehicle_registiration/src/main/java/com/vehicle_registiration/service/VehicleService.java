package com.vehicle_registiration.service;

import com.vehicle_registiration.entity.CustomUserDetails;
import com.vehicle_registiration.entity.UserVehicle;
import com.vehicle_registiration.entity.Vehicle;
import com.vehicle_registiration.repository.UserVehicleRepository;
import com.vehicle_registiration.repository.VehicleRepository;
import com.vehicle_registiration.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserVehicleRepository userVehicleRepository;


    public List<Vehicle> getAllVehicles(Long componyID) {
        return vehicleRepository.findAllByComponyId(componyID);
    }

    public Optional<Vehicle> getVehicleById(Long id,Long componyID) {
        return vehicleRepository.findByIdAndAndComponyId(id,componyID);
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Vehicle vehicle, CustomUserDetails customUserDetails) throws Exception {
        //Adminin sadece token ile gelen companyId sine bağlı araçları görebilmesi için böyle bir kontrol yapılmıştır
        Vehicle updated = getVehicleById(vehicle.getId(),customUserDetails.getCompanyId()).orElseThrow(() -> new Exception(Constants.NOT_FOUND_VEHICLE_ID + vehicle.getId()));

        updated.setLicensePlate(vehicle.getLicensePlate());
        updated.setComponyId(customUserDetails.getCompanyId());
        updated.setChassisNumber(vehicle.getChassisNumber());
        updated.setTag(vehicle.getTag());
        updated.setBrand(vehicle.getBrand());
        updated.setModel(vehicle.getModel());
        updated.setModelYear(vehicle.getModelYear());

        return createVehicle(updated);
    }

    public String deleteVehicle(Long id, Long componyID) {
        Optional<Vehicle> deletedVehicle = getVehicleById(id, componyID);
        if (deletedVehicle.isPresent()) {
            vehicleRepository.deleteById(id);
            return Constants.RECORD_ID + id + Constants.DELETED;
        } else {
            return Constants.DELETE_FAILED;
        }
    }

    public List<Vehicle> getAuthorizedVehicles(Long userId) {
        List<Long> vehicleIds = userVehicleRepository.findByUserId(userId).stream()
                .map(UserVehicle::getVehicleId).collect(Collectors.toList());

        return vehicleRepository.findAllById(vehicleIds);

    }
}
