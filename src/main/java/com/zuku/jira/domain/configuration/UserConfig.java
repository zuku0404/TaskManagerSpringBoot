package com.zuku.jira.domain.configuration;

import com.zuku.jira.domain.repository.IAccountRepository;
import com.zuku.jira.domain.repository.IUserRepository;
import com.zuku.jira.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    @Order(value = 2)
    CommandLineRunner userCommandLineRunner(IUserRepository userRepository, IAccountRepository accountRepository) {
        return args -> {
            userRepository.saveAll(List.of(
                    new User("John", "Doe", accountRepository.findById(1L).orElseThrow() ),
                    new User("Jane", "Smith", accountRepository.findById(2L).orElseThrow()),
                    new User("Michael", "Johnson", accountRepository.findById(3L).orElseThrow()),
                    new User("Emily", "Davis", accountRepository.findById(4L).orElseThrow()),
                    new User("William", "Brown", accountRepository.findById(5L).orElseThrow())));
        };
    }
}
