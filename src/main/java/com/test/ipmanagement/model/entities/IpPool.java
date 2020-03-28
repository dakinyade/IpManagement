package com.test.ipmanagement.model.entities;


import com.test.ipmanagement.util.IpUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

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


    public int getUppperBoundIntValue() {
        return IpUtil.splitIpAndGetPosition(
                this.upperBound,
                3);
    }

    public int getLowerBoundIntValue() {
        return IpUtil.splitIpAndGetPosition(
                this.lowerBound,
                3);
    }


}
