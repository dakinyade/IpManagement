package com.test.ipmanagement.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Login {
    @Id
    private int id;
    private String username;
    private String password;
}
