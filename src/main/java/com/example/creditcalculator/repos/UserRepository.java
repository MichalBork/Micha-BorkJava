package com.example.creditcalculator.repos;

import com.example.creditcalculator.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNameIgnoreCase(String name);
    List<User> findByName(String name);
}
