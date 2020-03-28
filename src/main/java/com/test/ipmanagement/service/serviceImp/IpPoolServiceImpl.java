package com.test.ipmanagement.service.serviceImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.ipmanagement.model.entities.IpPool;
import com.test.ipmanagement.model.repositories.IpPoolRepository;
import com.test.ipmanagement.service.IpPoolService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class IpPoolServiceImpl implements IpPoolService {

    @Autowired
    IpPoolRepository ipPoolRepository;

    @Override
    public void loadIpPool() throws IOException, ParseException {

        try {

            byte[] jsonData = Files.readAllBytes(Paths.get("ip-pools.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            IpPool[] ipPoolRetVal = objectMapper.readValue(jsonData, IpPool[].class);

            Arrays.asList(ipPoolRetVal).stream().forEach(e -> {
                IpPool ipPool = new IpPool();
                ipPool.setUpperBound(e.getUpperBound());
                ipPool.setDescription(e.getDescription());
                ipPool.setLowerBound(e.getLowerBound());
                ipPool.setId(e.getId());
                ipPool.setCapacityDemand(e.getCapacityDemand());

                this.ipPoolRepository.save(e);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public IpPool retrivePoolById(int ipPoolId) {

        IpPool ipPool = this.ipPoolRepository.findById(ipPoolId);

        return ipPool;
    }

    public boolean determineIpAllocation(IpPool ipPool, int lastIpRangeNo) {

        if ((lastIpRangeNo >= ipPool.getLowerBoundIntValue())
                &&
                (lastIpRangeNo <= ipPool.getUppperBoundIntValue())) {
            return true;
        }
        return false;
    }

}
