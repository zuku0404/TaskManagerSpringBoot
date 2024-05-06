package com.zuku.jira.data_initializer;

import com.zuku.jira.domain.repository.IBoardRepository;
import com.zuku.jira.entity.Board;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class BoardConfig {
    @Bean
    @Order(value = 3)
    CommandLineRunner boardCommandLineRunner(IBoardRepository boardRepository) {
        return args -> {
            boardRepository.saveAll(List.of(
                    new Board("Garden"),
                    new Board("Housework"),
                    new Board("Car"),
                    new Board("Grocery shopping"),
                    new Board("Home project")));
        };
    }
}
