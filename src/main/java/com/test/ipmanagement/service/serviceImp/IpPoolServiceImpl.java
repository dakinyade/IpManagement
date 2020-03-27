package com.test.ipmanagement.service.serviceImp;

import com.test.ipmanagement.model.entities.IpPool;
import com.test.ipmanagement.model.repositories.IpPoolRepository;
import com.test.ipmanagement.service.IpPoolService;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;

@Service
public class IpPoolServiceImpl implements IpPoolService {

    @Autowired
    IpPoolRepository ipPoolRepository;

    @Override
    public void loadIpPool() throws IOException, ParseException {


        try {
            Object obj = new JSONParser().parse(new FileReader("ip-pools.json"));

            JSONArray jo = (JSONArray) obj;


            Object id =  jo.get(1);
            Object description =  jo.get(2);
            Object capacityDemand = jo.get(3);
            Object lowerBound = jo.get(4);
            Object upperBound = jo.get(5);

            IpPool ipPool = new IpPool();
            ipPool.setId(Integer.valueOf(Integer.valueOf(id.toString())));
            ipPool.setDescription(description.toString());
            ipPool.setCapacityDemand(capacityDemand.toString());
            ipPool.setLowerBound(lowerBound.toString());
            ipPool.setUpperBound(upperBound.toString());
            this.ipPoolRepository.save(ipPool);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public IpPool retrivePoolById(int ipPoolId) {

        IpPool ipPool = this.ipPoolRepository.findById(ipPoolId);

        return ipPool;
    }


    public boolean determineIpAllocation(IpPool ipPool, int lastIpRangeNo) {

        int upperBound = Integer.valueOf(ipPool.getUpperBound());
        int lowerBound = Integer.valueOf(ipPool.getLowerBound());

        if ((lastIpRangeNo >= upperBound) && (lastIpRangeNo <= lowerBound)) {
            return true;
        }
        return false;
    }

}
