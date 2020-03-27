package com.test.ipmanagement.model.repositories;

import com.test.ipmanagement.model.entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    Login findByUsername(String username);

}
