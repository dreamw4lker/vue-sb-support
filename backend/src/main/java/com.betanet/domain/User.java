package com.betanet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import org.hibernate.annotations.Formula;

@Setter
@Table(name = "users", schema = "main")
@Getter
@Entity
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Size(max = 255)
    private String login;

    @Size(max = 255)
    private String password;
}
