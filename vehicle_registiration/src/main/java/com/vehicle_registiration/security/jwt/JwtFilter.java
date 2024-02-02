package com.vehicle_registiration.security.jwt;

import com.vehicle_registiration.security.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {

                String role = jwtUtil.extractClaim(jwt, claims -> claims.get("role", String.class));

                // Burada token ile gelen rolü spring security formatına dönüştürdüm
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase(Locale.ENGLISH));


                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(null, null, Collections.singletonList(authority));
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);

                if (isUserAllowedForRequest(userDetails, request)) {
                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isUserAllowedForRequest(UserDetails userDetails, HttpServletRequest request) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String requestMethod = request.getMethod();

        boolean isReadRequest = requestMethod.equalsIgnoreCase("GET");
        boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Admin kullanıcılar her türlü isteğe erişebilir.
        if (isAdmin) {
            return true;
        }

        // Ssandart kullanıcılar yalnızca okuma (GET) isteklerine erişebilir.
        if (isReadRequest) {
            return true;
        }

        // Diğer durumlar  reddedilir.
        return false;
    }
}
