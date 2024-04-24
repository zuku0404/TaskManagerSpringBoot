package com.zuku.jira.domain.configuration;

import com.zuku.jira.domain.repository.IAccountRepository;
import com.zuku.jira.entity.Account;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class AccountConfig {
    @Bean
    @Order(value = 1)
    static CommandLineRunner accountCommandLineRunner(IAccountRepository accountRepository) {
        return args -> {
            accountRepository.saveAll(List.of(
            new Account("john_doe", "john123_password"),
            new Account("jane_smith", "smith456_password"),
            new Account("michael_johnson", "michael789_password"),
            new Account("emily_davis", "emily456_password"),
            new Account("root", "itllphkw")));  //root password !!!
        };
    }
}
