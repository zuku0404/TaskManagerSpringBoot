package com.zuku.jira.data_initializer;

import com.zuku.jira.domain.repository.IBoardRepository;
import com.zuku.jira.domain.repository.ITaskRepository;
import com.zuku.jira.domain.repository.IUserRepository;
import com.zuku.jira.entity.Task;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class TaskConfig {

    @Bean
    @Order(value = 4)
    CommandLineRunner taskCommandLineRunner(ITaskRepository taskRepository, IUserRepository userRepository, IBoardRepository boardRepository) {
        return args -> {
            taskRepository.saveAll(List.of(new Task("Trim the lawn", "Trim the lawn in front of the house", userRepository.getReferenceById(1L), boardRepository.getReferenceById(1L)),
                    new Task("Plant flowers", "Plant new flowers in the garden", userRepository.getReferenceById(2L), boardRepository.getReferenceById(1L)),
                    new Task("Repair the fence", "Repair the damaged fence", userRepository.getReferenceById(3L), boardRepository.getReferenceById(1L)),
                    new Task("Make compost", "Prepare compost for fertilization", userRepository.getReferenceById(4L), boardRepository.getReferenceById(1L)),
                    new Task("Clean the kitchen", "Clean the entire kitchen", userRepository.getReferenceById(5L), boardRepository.getReferenceById(2L)),
                    new Task("Wash the windows", "Wash the windows in the living room and kitchen", userRepository.getReferenceById(1L), boardRepository.getReferenceById(2L)),
                    new Task("Read a book", "Read a new book", userRepository.getReferenceById(2L), boardRepository.getReferenceById(2L)),
                    new Task("Cook dinner", "Prepare dinner for the whole family", userRepository.getReferenceById(3L), boardRepository.getReferenceById(2L)),
                    new Task("Wash the car", "Thoroughly wash the car", userRepository.getReferenceById(4L), boardRepository.getReferenceById(3L)),
                    new Task("Refuel", "Refuel the car at the station", userRepository.getReferenceById(5L), boardRepository.getReferenceById(3L)),
                    new Task("Fix the tire", "Fix the punctured tire", userRepository.getReferenceById(1L), boardRepository.getReferenceById(3L)),
                    new Task("Change oil", "Change the oil in the engine", userRepository.getReferenceById(2L), boardRepository.getReferenceById(3L)),
                    new Task("Do the grocery shopping", "Do the grocery shopping", userRepository.getReferenceById(3L), boardRepository.getReferenceById(4L)),
                    new Task("Buy vegetables", "Buy fresh vegetables for dinner", userRepository.getReferenceById(4L), boardRepository.getReferenceById(4L)),
                    new Task("Purchase fruits", "Purchase fresh fruits for breakfast", userRepository.getReferenceById(5L), boardRepository.getReferenceById(4L)),
                    new Task("Get bread", "Get some bread from the bakery", userRepository.getReferenceById(1L), boardRepository.getReferenceById(4L)),
                    new Task("Plan the home project", "Plan the next home improvement project", userRepository.getReferenceById(2L), boardRepository.getReferenceById(5L)),
                    new Task("Buy materials", "Buy necessary materials for the project", userRepository.getReferenceById(3L), boardRepository.getReferenceById(5L)),
                    new Task("Contact contractor", "Contact a contractor for further details", userRepository.getReferenceById(4L), boardRepository.getReferenceById(5L)),
                    new Task("Start renovation", "Start the renovation process", userRepository.getReferenceById(5L), boardRepository.getReferenceById(5L))));
        };
    }
}
