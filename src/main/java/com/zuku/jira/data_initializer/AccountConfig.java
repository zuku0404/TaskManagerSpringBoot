package com.zuku.jira.data_initializer;

import com.zuku.jira.domain.repository.IAccountRepository;
import com.zuku.jira.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AccountConfig {
    private final PasswordEncoder passwordEncoder;
    @Bean
    @Order(value = 1)
    CommandLineRunner accountCommandLineRunner(IAccountRepository accountRepository) {
        return args -> {
            accountRepository.saveAll(List.of(
            new Account("john", passwordEncoder.encode("1234")),
            new Account("jane", passwordEncoder.encode("1234")),
            new Account("michael", passwordEncoder.encode("aaa")),
            new Account("emily", passwordEncoder.encode("bbb")),
            new Account("root", passwordEncoder.encode("password"))));
        };
    }
}
