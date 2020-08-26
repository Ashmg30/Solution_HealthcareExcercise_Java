package com.test.healthcare.config;

import com.test.healthcare.model.Dependent;
import com.test.healthcare.model.Enrollee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.MediaType;

@Configuration
public class HealthCarePortalConfig {
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer()
    {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(Enrollee.class);
            config.exposeIdsFor(Dependent.class);
        });
    }
}
