package com.backend.authsystem.authentication.service;


import com.backend.authsystem.authentication.config.SecurityEnvironments;
import com.backend.authsystem.authentication.entity.RefreshTokenEntity;
import com.backend.authsystem.authentication.entity.AccountEntity;
import com.backend.authsystem.authentication.repository.RefreshTokenRepository;
import com.backend.authsystem.authentication.util.HashToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;


@Service
public class JwtService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final SecurityEnvironments securityEnvironments;
    private final SecretKey key;

    @Autowired
    public JwtService(RefreshTokenRepository refreshTokenRepository, SecurityEnvironments securityEnvironments){
        this.refreshTokenRepository = refreshTokenRepository;
        this.securityEnvironments = securityEnvironments;
        this.key = Keys.hmacShaKeyFor(securityEnvironments.getTokenSecret().getBytes(StandardCharsets.UTF_8));
    }


    public static List<String> extractRoles(AccountEntity user) {
        return user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .toList();
    }

    public static List<String> extractPermissions(AccountEntity user) {
        return user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(perm -> perm.getPermissionName().name())
                .distinct() // optional: remove duplicates
                .toList();
    }

    public String generateAccessTokenFromUser(AccountEntity user) {
        Map<String, Object> claims = new HashMap<>();

        // Use the new helpers
        List<String> roles = extractRoles(user);
        List<String> permissions = extractPermissions(user);

        claims.put("roles", roles);
        claims.put("permissions", permissions);
        return Jwts.builder()
                .setId(String.valueOf(user.getUserId()))
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(securityEnvironments.getTokenExpirationInMinutes(), ChronoUnit.MINUTES)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Refresh Token Methods
    public String generateRefreshToken(AccountEntity user) {

        String plainToken = UUID.randomUUID().toString();
        String tokenHash = HashToken.hashToken(plainToken);


        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .tokenId(UUID.randomUUID())
                .myUser(user)
                .tokenHash(tokenHash)
                .expiresAt(Instant.now().plus(
                        securityEnvironments.getRefreshTokenExpirationInDays(),
                        ChronoUnit.DAYS))
                .createdAt(Instant.now())
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);
        return plainToken;
    }



}