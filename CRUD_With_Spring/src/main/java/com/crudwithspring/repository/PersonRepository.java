package com.crudwithspring.repository;

import org.springframework.data.repository.CrudRepository;

import com.crudwithspring.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
