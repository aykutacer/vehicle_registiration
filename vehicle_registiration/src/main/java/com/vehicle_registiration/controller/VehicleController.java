package com.vehicle_registiration.controller;

import com.vehicle_registiration.entity.CustomUserDetails;
import com.vehicle_registiration.entity.Vehicle;
import com.vehicle_registiration.repository.GroupRepository;
import com.vehicle_registiration.repository.UserVehicleRepository;
import com.vehicle_registiration.repository.VehicleRepository;
import com.vehicle_registiration.service.UserService;
import com.vehicle_registiration.service.VehicleService;
import com.vehicle_registiration.util.Constants;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserVehicleRepository userVehicleRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private GroupRepository groupRepository;

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);


    @ApiOperation(value = "create", notes = "create")
    @PreAuthorize("hasRole( 'ROLE_ADMIN')")
    @PostMapping("/admin/create")
    public String create(
            HttpServletRequest request,
            @RequestBody Vehicle vehicle) {
        CustomUserDetails customUserDetails = userService.authorizationUser(request);

        vehicle.setComponyId(customUserDetails.getCompanyId());
        Vehicle result = vehicleService.createVehicle(vehicle);
        if (result.getId() != null) {
            logger.info(Constants.CREATE_SUCCESS);
            // proje genelinde logger kullanarak logları logs/myapp.log dizini içinde tuttum.
            // Ancak gerçek bir projede Business olarak olarak tutulmalı.
            // Bunun için DB de log tablosu oluşturma yolu veya elasticSearch kullanılabilir.
            return Constants.CREATE_SUCCESS;
        } else {
            logger.error(Constants.CREATE_FAILED);
            return Constants.CREATE_FAILED;
        }
    }

    @ApiOperation(value = "update", notes = "update")
    @PreAuthorize("hasRole( 'ROLE_ADMIN')")
    @PostMapping("/admin/update")
    public String update(
            HttpServletRequest request,
            @RequestBody Vehicle vehicle) throws Exception {
        CustomUserDetails customUserDetails = userService.authorizationUser(request);
        Vehicle result = vehicleService.updateVehicle(vehicle, customUserDetails);
        if (result.getId() != null) {
            logger.info(Constants.RECORD_ID + result.getId() + Constants.UPDATED);
            return Constants.RECORD_ID + result.getId() + Constants.UPDATED;
        } else {
            logger.error(Constants.UPDATE_FAILED);
            return Constants.UPDATE_FAILED;
        }
    }

    @ApiOperation(value = "delete", notes = "delete")
    @PreAuthorize("hasRole( 'ROLE_ADMIN')")
    @PostMapping("/admin/delete")
    public String delete(
            HttpServletRequest request,
            @RequestParam("id") long id) {
        CustomUserDetails customUserDetails = userService.authorizationUser(request);
        String result = vehicleService.deleteVehicle(id, customUserDetails.getCompanyId());
        logger.info(result);
        return result;
    }


    @ApiOperation(value = "findAll", notes = "findAll")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STANDART')")
    @GetMapping("/findAll")
    public List<Vehicle> getAllVehicles(HttpServletRequest request) {
        CustomUserDetails customUserDetails = userService.authorizationUser(request);
        return vehicleService.getAllVehicles(customUserDetails.getCompanyId());
    }

    @ApiOperation(value = "findById", notes = "findById")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STANDART')")
    @GetMapping("/findById")
    public Vehicle getVehicleById(
            HttpServletRequest request,
            @RequestParam("id") Long id
    ) throws Exception {
        CustomUserDetails customUserDetails = userService.authorizationUser(request);
        return vehicleService.getVehicleById(id, customUserDetails.getCompanyId())
                .orElseThrow(() -> new Exception(Constants.NOT_FOUND_VEHICLE_ID + id));
    }

    @ApiOperation(value = "vehicleList", notes = "vehicleList")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STANDART')")
    @GetMapping("/vehicleList")
    public List<Vehicle> getAuthorizedVehicles(
            HttpServletRequest request) throws Exception {
        CustomUserDetails customUserDetails = userService.authorizationUser(request);
        List<Long> authorizedVehicleIds = userVehicleRepository.findVehicleIdsByUserId(customUserDetails.getUserId());
        return vehicleRepository.findByIdInAndAndComponyId(authorizedVehicleIds, customUserDetails.getCompanyId());
    }


}
