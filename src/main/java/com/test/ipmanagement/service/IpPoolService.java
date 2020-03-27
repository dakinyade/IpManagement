package com.test.ipmanagement.service;

import com.test.ipmanagement.model.entities.IpPool;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface IpPoolService {

    void loadIpPool() throws IOException, ParseException;

    IpPool retrivePoolById(int ipPoolId);

    boolean determineIpAllocation(IpPool ipPool, int lastIpRangeNo);

}
