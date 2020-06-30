package com.betanet.service;

public interface ConfigurationService {
    long getJwtExpirationTime();

    String getJwtSecret();
}
