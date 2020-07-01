package com.betanet.service.impl;

import com.betanet.service.api.ConfigurationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    @Value("${jwt.expiration}")
    private long JWT_TOKEN_EXPIRATION;

    @Value("${jwt.secret}")
    private String SECRET;

    @Override
    public long getJwtExpirationTime() {
        return JWT_TOKEN_EXPIRATION;
    }

    @Override
    public String getJwtSecret() {
        return SECRET;
    }
}
