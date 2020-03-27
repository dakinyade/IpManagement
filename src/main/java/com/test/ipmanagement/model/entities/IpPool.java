package com.test.ipmanagement.model.entities;


import com.test.ipmanagement.model.ResourceState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IpPool {

    @Id
    private int id;

    private String description;
    private int capacity;
    private String capacityDemand;
    private String lowerBound;
    private String upperBound;

}
