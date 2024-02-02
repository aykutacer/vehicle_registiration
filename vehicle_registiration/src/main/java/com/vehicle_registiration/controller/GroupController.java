package com.vehicle_registiration.controller;

import com.vehicle_registiration.entity.CustomUserDetails;
import com.vehicle_registiration.model.GroupTree;
import com.vehicle_registiration.service.GroupService;
import com.vehicle_registiration.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class GroupController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @ApiOperation(value = "treeList", notes = "treeList")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STANDART')")
    @GetMapping("/treeList")
    public ResponseEntity<GroupTree> getGroupTree(
            HttpServletRequest request,
            @RequestParam("groupId") Long groupId) throws Exception {
        CustomUserDetails customUserDetails = userService.authorizationUser(request);
        GroupTree groupTree = groupService.getGroupTree(customUserDetails.getUserId(), groupId, customUserDetails.getCompanyId());
        return ResponseEntity.ok(groupTree);
    }

}
