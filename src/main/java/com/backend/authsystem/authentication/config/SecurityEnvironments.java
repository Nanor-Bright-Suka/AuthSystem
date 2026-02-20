package com.backend.authsystem.authentication.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SecurityEnvironments {
    public  String tokenSecret;
    public  Integer tokenExpirationInMinutes;
    public  Integer refreshTokenExpirationInDays;

    @Autowired
    public SecurityEnvironments(
            @Value("${token.secret}") String tokenSecret,
            @Value("${token.expiration-in-minutes}") Integer tokenExpirationInMinutes,
            @Value("${refresh.token-expiration-in-days}") Integer refreshTokenExpirationInDays
    ){
        this.tokenSecret = tokenSecret;
        this.tokenExpirationInMinutes= tokenExpirationInMinutes;
        this.refreshTokenExpirationInDays= refreshTokenExpirationInDays;
    }


}
