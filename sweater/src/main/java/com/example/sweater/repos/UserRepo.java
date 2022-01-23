package com.example.sweater.repos;

import com.example.sweater.domain.UserAndDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserAndDetails, Long> {

    // https://docs.spring.io/spring-data/jpa/docs/1.6.0.RELEASE/reference/html/jpa.repositories.html
    UserAndDetails findByUsername(String username);

    UserAndDetails findByActivationCode(String code);
}
