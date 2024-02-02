package com.vehicle_registiration.security.security;

import com.vehicle_registiration.entity.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class DummyUserService {

    private Map<String, CustomUserDetails> users = new HashMap<>();

    @PostConstruct
    public void initialize() {
        List<SimpleGrantedAuthority> adminAuthorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        List<SimpleGrantedAuthority> userAuthorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_STANDART"));

        CustomUserDetails adminUser = new CustomUserDetails();
        adminUser.setUsername("admin");
        adminUser.setPassword("admin123");
        adminUser.setAuthorities(adminAuthorities);
        adminUser.setUserId(1547L);
        adminUser.setName("Faik Aykut");
        adminUser.setSurname("ACER");
        adminUser.setCompanyId(8579L);
        adminUser.setCompanyName("Gib Teknoloji");
        adminUser.setRole("Admin");

        users.put("admin", adminUser);


    }

    public CustomUserDetails getUserByUsername(String username) {
        return users.get(username);
    }
}
