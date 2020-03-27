package com.test.ipmanagement.model.repositories;

import com.test.ipmanagement.model.entities.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpAddressRepository extends JpaRepository<IpAddress, Long> {

     IpAddress findByValue(String ipAddress);

}
