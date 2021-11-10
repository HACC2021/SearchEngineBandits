package com.hacc2021.searchenginebandits.animalqueue;

import com.hacc2021.searchenginebandits.animalqueue.repository.AdminRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = AdminRepository.class)
public class AnimalQueueApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnimalQueueApplication.class, args);
    }
}
