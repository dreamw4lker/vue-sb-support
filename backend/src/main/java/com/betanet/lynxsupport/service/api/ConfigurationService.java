package com.betanet.lynxsupport.service.api;

public interface ConfigurationService {
    long getJwtExpirationTime();

    String getJwtSecret();
}
