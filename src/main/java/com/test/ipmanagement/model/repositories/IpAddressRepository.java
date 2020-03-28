package com.test.ipmanagement.model.repositories;

import com.test.ipmanagement.model.entities.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IpAddressRepository extends JpaRepository<IpAddress, Long> {

     IpAddress findByValue(String ipAddress);

     @Transactional
     @Modifying
     @Query("update IpAddress i set  i.resourceState = ?1 where i.value= ?2")
     void updateIpAddress(String resourceState, String ip);
}
