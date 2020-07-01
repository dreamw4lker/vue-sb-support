package com.betanet.service.api;

public interface ConfigurationService {
    long getJwtExpirationTime();

    String getJwtSecret();
}
