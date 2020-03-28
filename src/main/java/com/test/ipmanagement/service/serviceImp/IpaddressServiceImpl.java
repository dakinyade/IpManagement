package com.test.ipmanagement.service.serviceImp;

import com.test.ipmanagement.model.entities.IpAddress;
import com.test.ipmanagement.model.entities.IpPool;
import com.test.ipmanagement.model.repositories.IpAddressRepository;
import com.test.ipmanagement.model.repositories.IpPoolRepository;
import com.test.ipmanagement.service.IpPoolService;
import com.test.ipmanagement.service.IpaddressService;
import com.test.ipmanagement.util.IpUtil;
import com.test.ipmanagement.util.ResourceState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpaddressServiceImpl implements IpaddressService {

    IpPoolService ipPoolService;
    IpAddressRepository ipAddressRepository;

    @Autowired
    public IpaddressServiceImpl(IpPoolService ipPoolService, IpAddressRepository ipAddressRepository, IpPoolRepository ipPoolRepository) {
        this.ipPoolService = ipPoolService;
        this.ipAddressRepository = ipAddressRepository;
        this.ipPoolRepository = ipPoolRepository;
    }

    IpPoolRepository ipPoolRepository;

    @Override
    public void checkeservedIp(String ip, int poolid) throws Exception {
        IpAddress ipAddress;
        ipAddress = this.ipAddressRepository.findByValue(ip);
        IpPool ipPool = this.ipPoolService.retrivePoolById(poolid);
        int lastIpRangeNo = IpUtil.splitIpAndGetPosition(ip, 3);
        if (ipAddress == null) {

            if ((!(lastIpRangeNo >= ipPool.getLowerBoundIntValue())
                    && !(lastIpRangeNo <= ipPool.getUppperBoundIntValue()))) {
                throw new Exception("throw new Exception(Ip is blacklisted or Reserved)");
            }
        }
        if (!(ipAddress == null)) {

            if (ipAddress.getResourceState().equals(ResourceState.Blacklisted.name())
                    || ipAddress.getResourceState().equals(ResourceState.Reserved.name())
                    || (!(lastIpRangeNo >= ipPool.getLowerBoundIntValue())
                    && !(lastIpRangeNo <= ipPool.getUppperBoundIntValue()))) {
                throw new Exception();
            }

        }
    }

    @Override
    public void blackListIp(String ip, int poolid) throws Exception {
        IpPool ipPool;
        IpAddress ipAddress;
        ipPool = this.ipPoolRepository.findById(poolid);
        ipAddress = this.ipAddressRepository.findByValue(ip);
        if (ipAddress == null) {
            if (!(IpUtil.isIpWithinPoolRange(ipPool, ip)))
                throw new Exception();
        }
        if (!(ipAddress == null)) {
            if (!(IpUtil.isIpWithinPoolRange(ipPool, ip))
                    || ipAddress.getResourceState().equals(ResourceState.Reserved)) ;
            throw new Exception("Ip Already Reserved");
        }
    }

    @Override
    public void freeAnIp(String ip, int poolid, IpPool ipPool) throws Exception {

        if (!(IpUtil.isIpWithinPoolRange(ipPool, ip))) {
            throw new Exception("The Ip is not with the pool range");
        }

        //if exhist already
        IpAddress ipAddress = this.ipAddressRepository.findByValue(ip);

        if (!(ipAddress == null)) {
            ipAddress.setResourceState(ResourceState.Free.name());
            this.ipAddressRepository.updateIpAddress(ResourceState.Free.name(), ip);
        } else {
            ipAddress = new IpAddress();
            ipAddress.setValue(ip);
            ipAddress.setIpPoolid(poolid);
            ipAddress.setResourceState(ResourceState.Free.name());
            this.ipAddressRepository.save(ipAddress);
        }
    }

    @Override
    public void processIp(String ip, int poolid) {
        IpPool ipPool = this.ipPoolService.retrivePoolById(poolid);
        int lastIpRangeNo = IpUtil.splitIpAndGetPosition(ip, 3);
        if (this.ipPoolService.determineIpAllocation(ipPool, lastIpRangeNo)) {
            IpAddress ipAddress = new IpAddress();
            ipAddress.setIpPoolid(poolid);
            ipAddress.setValue(ip);
            ipAddress.setResourceState("Reserved");
            this.ipAddressRepository.save(ipAddress);
        }


    }
}
