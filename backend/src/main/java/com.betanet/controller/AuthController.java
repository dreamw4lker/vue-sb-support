package com.betanet.controller;

import com.betanet.domain.User;
import com.betanet.domain.bean.CredentialsBean;
import com.betanet.domain.bean.UserBean;
import com.betanet.service.api.ConfigurationService;
import com.betanet.service.api.UserService;
import com.betanet.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigurationService configurationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody CredentialsBean credentialsBean) {
        try {
            authenticate(credentialsBean.getLogin(), credentialsBean.getPassword());
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok("user.notFound");
        }
        User user = userService.getByLogin(credentialsBean.getLogin());
        UserBean bean = new UserBean(user.getLogin());
        final String token;
        if (credentialsBean.isRemember()) {
            token = JwtTokenUtil.generateToken(bean, configurationService.getJwtSecret(), 60 * 60 * 24 * 365);
        } else {
            token = JwtTokenUtil.generateToken(bean, configurationService.getJwtSecret(), configurationService.getJwtExpirationTime());
        }

        return ResponseEntity.ok(token);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
