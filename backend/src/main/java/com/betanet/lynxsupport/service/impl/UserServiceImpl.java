package com.betanet.lynxsupport.service.impl;

import com.betanet.lynxsupport.domain.User;
import com.betanet.lynxsupport.domain.QUser;
import com.betanet.lynxsupport.repository.UserRepository;
import com.betanet.lynxsupport.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    QUser quser = QUser.user;

    @Override
    public User getByLogin(String login) {
        User user = userRepository.findOne(quser.login.eq(login)).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(login + " not found");
        }
        return user;
    }
}
