package com.test.ipmanagement.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IpAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int  ipPoolid;
    private String value;
    private String resourceState;

}
