package com.test.ipmanagement.model.repositories;

import com.test.ipmanagement.model.entities.IpPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpPoolRepository extends JpaRepository<IpPool, Long> {

    IpPool findById(int id);

}
