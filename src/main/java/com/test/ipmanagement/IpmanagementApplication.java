package com.test.ipmanagement;

import com.test.ipmanagement.service.IpPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
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

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.test.ipmanagement")).build();
    }
}
