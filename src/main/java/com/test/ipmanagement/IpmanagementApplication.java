package com.test.ipmanagement;

import com.test.ipmanagement.service.IpPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IpmanagementApplication implements
        CommandLineRunner {

    @Autowired
    IpPoolService ipPoolService;

    public static void main(String[] args) {
        SpringApplication.run(IpmanagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ipPoolService.loadIpPool();

    }
}
