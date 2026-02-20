package com.backend.authsystem.authentication.service;


import com.backend.authsystem.authentication.entity.PermissionEntity;
import com.backend.authsystem.authentication.entity.RoleEntity;
import com.backend.authsystem.authentication.entity.AccountEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public record CustomUserDetails(AccountEntity user) implements UserDetails {

    @Override
    public @NotNull Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (RoleEntity role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().name()));

            for (PermissionEntity perm : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(perm.getPermissionName().name()));
            }
        }

        return authorities;

    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public @NotNull String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}