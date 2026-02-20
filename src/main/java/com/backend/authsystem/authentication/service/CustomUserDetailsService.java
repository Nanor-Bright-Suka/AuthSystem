package com.backend.authsystem.authentication.service;

import com.backend.authsystem.authentication.entity.AccountEntity;
import com.backend.authsystem.authentication.repository.AccountRepository;

import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements  UserDetailsService {

        private final AccountRepository userRepository;

        public CustomUserDetailsService(AccountRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public @NotNull UserDetails loadUserByUsername(@NotNull String email) {
            AccountEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new CustomUserDetails(user);
        }




    }
