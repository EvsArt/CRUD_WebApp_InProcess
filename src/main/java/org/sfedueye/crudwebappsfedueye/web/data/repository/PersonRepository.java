package org.sfedueye.crudwebappsfedueye.web.data.repository;

import org.springframework.data.domain.Pageable;

import org.sfedueye.crudwebappsfedueye.web.data.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Page<Person> findAllByAcceptedIsFalseOrderBySurname(Pageable pageable);
    Page<Person> findAllByAcceptedIsTrueOrderBySurname(Pageable pageable);
    int countAllByAcceptedIsFalse();
    int countAllByAcceptedIsTrue();
    boolean existsByEmail(String email);
    Person findByEmail(String email);
}
