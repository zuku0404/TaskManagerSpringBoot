package com.zuku.jira.data_initializer;

import com.zuku.jira.domain.repository.IAccountRepository;
import com.zuku.jira.domain.repository.IUserRepository;
import com.zuku.jira.entity.User;
import com.zuku.jira.domain.enums.Role;
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
                    new User("John", "Doe", Role.USER, accountRepository.findById(1L).orElseThrow() ),
                    new User("Jane", "Smith", Role.USER, accountRepository.findById(2L).orElseThrow()),
                    new User("Michael", "Johnson", Role.USER, accountRepository.findById(3L).orElseThrow()),
                    new User("Emily", "Davis", Role.USER, accountRepository.findById(4L).orElseThrow()),
                    new User("William", "Brown", Role.USER, accountRepository.findById(5L).orElseThrow())));
        };
    }
}
