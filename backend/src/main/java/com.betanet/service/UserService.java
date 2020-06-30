package com.betanet.service;

import com.betanet.domain.User;

public interface UserService {
    User getByLogin(String login);
}
