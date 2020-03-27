package com.test.ipmanagement.controller;

import com.test.ipmanagement.model.entities.IpAddress;
import com.test.ipmanagement.model.entities.IpPool;
import com.test.ipmanagement.model.repositories.IpAddressRepository;
import com.test.ipmanagement.service.IpPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IpAddressController {

    @Autowired
    IpPoolService ipPoolService;
    IpAddressRepository ipAddressRepository;

    @PostMapping(path = "/processip", consumes = {
            MediaType.APPLICATION_PROBLEM_XML_VALUE,
            MediaType.APPLICATION_PROBLEM_XML_VALUE
    })
    public ResponseEntity<?> processIp(@RequestParam String ip, int ipPoolid) {

        IpPool ipPool = this.ipPoolService.retrivePoolById(ipPoolid);

        String[] splitedIp = ip.split(".");
        String lastIpRangeNo = splitedIp[3];
        boolean allocatable = false;
        if (this.ipPoolService.determineIpAllocation(ipPool, Integer.valueOf(lastIpRangeNo))) {

            IpAddress ipAddress = new IpAddress();
            ipAddress.setIpPoolid(ipPoolid);
            ipAddress.setValue(ip);
            ipAddress.setResourceState("Reserved");
            this.ipAddressRepository.save(ipAddress);

            return new ResponseEntity<>(ipAddress, HttpStatus.OK);
        }

        return new ResponseEntity<>("please try to reallocate to a bigger resource",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping(path = "/checkedreserve", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<Object> checkReservedIp(@RequestParam String ip, int ipPoolId) throws Exception {

        IpAddress ipAddress = new IpAddress();
        try {
            ipAddress = this.ipAddressRepository.findByValue(ip);

            IpPool ipPool = this.ipPoolService.retrivePoolById(ipPoolId);
            String[] splitedIp = ip.split(".");
            String lastIpRangeNo = splitedIp[3];


            if (ipAddress.getResourceState() == "Blacklisted" || ipAddress.getResourceState() == "Reserved") {
                return new ResponseEntity(ipAddress.getResourceState(), HttpStatus.INTERNAL_SERVER_ERROR);

            }
        } catch (Exception exception) {
            throw new Exception("Ip is blaclisted or Reserved");

        }

        return new ResponseEntity<>(ipAddress + "Not Reserved", HttpStatus.OK);
    }


}
