package com.example.creditcalculator.repos;

import com.example.creditcalculator.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
