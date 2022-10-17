package com.example.creditcalculator.repos;

import com.example.creditcalculator.domain.Person;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, UUID> {
}
