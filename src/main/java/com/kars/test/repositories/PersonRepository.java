package com.kars.test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kars.test.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}
