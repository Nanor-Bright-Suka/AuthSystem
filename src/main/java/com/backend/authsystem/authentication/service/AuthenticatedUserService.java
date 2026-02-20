package com.backend.authsystem.authentication.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticatedUserService {
    public String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        System.out.println("Authenticated user: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());

        return auth.getName();
    }
}
