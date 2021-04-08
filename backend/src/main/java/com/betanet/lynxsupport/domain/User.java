package com.betanet.lynxsupport.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Table(name = "users", schema = "main")
@Getter
@Entity
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(max = 255)
    private String login;

    @Size(max = 255)
    private String password;
}
