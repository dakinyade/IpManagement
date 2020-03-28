package com.test.ipmanagement.controller;

import com.test.ipmanagement.model.entities.IpAddress;
import com.test.ipmanagement.model.entities.IpPool;
import com.test.ipmanagement.model.repositories.IpAddressRepository;
import com.test.ipmanagement.model.repositories.IpPoolRepository;
import com.test.ipmanagement.service.IpPoolService;
import com.test.ipmanagement.service.IpaddressService;
import com.test.ipmanagement.util.IpUtil;
import com.test.ipmanagement.util.ResourceState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class IpAddressController {

    IpPoolService ipPoolService;
    IpAddressRepository ipAddressRepository;
    IpPoolRepository ipPoolRepository;
    IpaddressService ipaddressService;

    @Autowired
    public IpAddressController(IpAddressRepository ipAddressRepository,
                               IpPoolRepository ipPoolRepository,
                               IpPoolService ipPoolService,
                               IpaddressService ipaddressService) {
        this.ipAddressRepository = ipAddressRepository;
        this.ipPoolRepository = ipPoolRepository;
        this.ipPoolService = ipPoolService;
        this.ipaddressService = ipaddressService;
    }

    //==============QUESTION 1=====================
    @PostMapping(path = "/processip")
    public ResponseEntity<?> processIp(@RequestParam(value = "ip", required = true) String ip,
                                       @RequestParam(value = "poolid", required = true) int ipPoolid) {

        IpPool ipPool = this.ipPoolService.retrivePoolById(ipPoolid);
        int lastIpRangeNo = IpUtil.splitIpAndGetPosition(ip, 3);
        boolean allocatable = false;
        if (this.ipPoolService.determineIpAllocation(ipPool, lastIpRangeNo)) {
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

    //==============QUESTION 2====================
    @GetMapping(path = "/checkedreserve")
    public ResponseEntity<Object> checkReservedIp(@RequestParam(value = "ip") String ip,
                                                  @RequestParam(value = "poolid") int ipPoolId) throws Exception {

        try {
            this.ipaddressService.checkeservedIp(ip, ipPoolId);
        } catch (Exception exception) {
            log.info(exception.toString());
            return new ResponseEntity<>("Ip Is Blacklisted or Reserved!", HttpStatus.ALREADY_REPORTED);

        }
        return new ResponseEntity<>(ip + " Is Not Reserved", HttpStatus.OK);
    }


    //================Question 3=====================
    @PostMapping(path = "/blacklisted")
    public ResponseEntity<Object> retrieveBlackList(@RequestParam(value = "ip", required = true) String ip,
                                                    @RequestParam(value = "poolid", required = true) int poolId) throws Exception {

        try {
            this.ipaddressService.blackListIp(ip, poolId);
        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        IpAddress ipAddress;
        ipAddress = new IpAddress();
        ipAddress.setValue(ip);
        ipAddress.setIpPoolid(poolId);
        ipAddress.setResourceState(ResourceState.Blacklisted.name());
        this.ipAddressRepository.save(ipAddress);

        return new ResponseEntity<>(ipAddress + " Blacklisted Successfully", HttpStatus.OK);
    }

    //================QUESTION 4 ======================
    @PatchMapping(path = "/freeip")
    public ResponseEntity<Object> freeAnIp(@RequestParam(value = "ip", required = true) String ip,
                                           @RequestParam(value = "poolid", required = true) int poolId)
            throws Exception {
        IpPool ipPool = this.ipPoolRepository.findById(poolId);
        try {
            this.ipaddressService.freeAnIp(ip, poolId, ipPool);
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Ip Freed", HttpStatus.OK);
    }

    //===============Question 5=============================
    @GetMapping(path = "/getip/{ip}")
    public ResponseEntity<Object> getIpByIp(@PathVariable(value = "ip", required = true) String ip) {
        IpAddress ipAddress;
        try {
            ipAddress = Optional.of(this.ipAddressRepository.findByValue(ip)).orElseThrow(Exception::new);
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return new ResponseEntity<>("IP Address not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ipAddress + "  IP Address found", HttpStatus.OK);

    }

    //=============Extras=========================
    @GetMapping(path = "/poollist")
    public ResponseEntity<Object> getIpPoolList() {

        return new ResponseEntity<>(this.ipPoolRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping(path = "/ipllist")
    public ResponseEntity<Object> getIpList() {
        return new ResponseEntity<>(this.ipAddressRepository.findAll(), HttpStatus.OK);
    }

}
