package com.betanet.service.api;

import com.betanet.domain.User;

public interface UserService {
    User getByLogin(String login);
}
