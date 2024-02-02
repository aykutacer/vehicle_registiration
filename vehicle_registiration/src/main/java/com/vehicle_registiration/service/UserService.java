package com.vehicle_registiration.service;

import com.vehicle_registiration.entity.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;
import com.vehicle_registiration.security.jwt.JwtUtil;
import com.vehicle_registiration.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JwtUtil jwtUtil;

    public CustomUserDetails authorizationUser(HttpServletRequest request) throws UsernameNotFoundException {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            customUserDetails.setUsername(jwtUtil.extractClaim(jwt, claims -> claims.get("sub", String.class)));
            customUserDetails.setCompanyId(jwtUtil.extractClaim(jwt, claims -> claims.get("companyId", Long.class)));
            customUserDetails.setRole(jwtUtil.extractClaim(jwt, claims -> claims.get("role", String.class)));
            customUserDetails.setSurname(jwtUtil.extractClaim(jwt, claims -> claims.get("surname", String.class)));
            customUserDetails.setCompanyName(jwtUtil.extractClaim(jwt, claims -> claims.get("companyName", String.class)));
            customUserDetails.setName(jwtUtil.extractClaim(jwt, claims -> claims.get("name", String.class)));
            customUserDetails.setUserId(jwtUtil.extractClaim(jwt, claims -> claims.get("userId", Long.class)));
            return customUserDetails;

        } else {
            throw new UsernameNotFoundException(Constants.NOT_FOUND_USER);
        }
    }
}

