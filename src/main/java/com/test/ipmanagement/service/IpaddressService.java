package com.test.ipmanagement.service;

import com.test.ipmanagement.model.entities.IpPool;

public interface IpaddressService {

    void checkeservedIp(String ip, int poolid) throws Exception;

    void blackListIp(String ip, int poolid) throws Exception;

    void freeAnIp(String ip, int poolid, IpPool ipPool) throws Exception;

    void processIp(String ip, int poolid);
}
