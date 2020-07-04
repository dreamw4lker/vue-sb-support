package com.betanet.lynxsupport.domain.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CredentialsBean {
    private String login;
    private String password;
    private boolean remember;
}
