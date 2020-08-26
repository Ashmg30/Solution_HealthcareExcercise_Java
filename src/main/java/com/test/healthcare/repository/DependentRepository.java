package com.test.healthcare.repository;

import com.test.healthcare.model.Dependent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DependentRepository extends CrudRepository<Dependent, Long> {
}
