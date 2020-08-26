package com.test.healthcare;

import com.test.healthcare.repository.DependentRepository;
import com.test.healthcare.repository.EnrolleeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HealthCarePortalApplicationTests {

	@Autowired
	private EnrolleeRepository enrolleeRepository;

	@Autowired
	private DependentRepository dependentRepository;

	@Test
	void contextLoads() {
		assertThat(enrolleeRepository).isNotNull();
		assertThat(dependentRepository).isNotNull();
	}

}
