package com.test.healthcare.repository;

import com.test.healthcare.model.Enrollee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EnrolleeRepository extends CrudRepository<Enrollee, Long> {
}
