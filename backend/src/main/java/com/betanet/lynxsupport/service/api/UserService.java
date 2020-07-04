package com.betanet.lynxsupport.service.api;

import com.betanet.lynxsupport.domain.User;

public interface UserService {
    User getByLogin(String login);
}
