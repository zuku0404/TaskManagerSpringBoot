package com.zuku.jira.domain.service.impl;

import com.zuku.jira.domain.enums.ActionResultDescription;
import com.zuku.jira.domain.repository.ITaskRepository;
import com.zuku.jira.domain.repository.IUserRepository;
import com.zuku.jira.domain.service.IUserService;
import com.zuku.jira.domain.validator.Validator;
import com.zuku.jira.entity.Task;
import com.zuku.jira.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final ITaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponseEntity<Object> unregister() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Task> allByUserId = taskRepository.findAllByUserId(user.getId());
        for (Task task : allByUserId) {
            task.setUser(null);
        }
        userRepository.deleteById(user.getId());
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Object> editUserData(String firstName, String lastName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (firstName != null && !firstName.isBlank() && !Objects.equals(user.getName(), firstName)) {
            userRepository.findById(user.getId()).get().setName(firstName);
        }
        if (lastName != null && !lastName.isBlank() && !Objects.equals(user.getLastName(), lastName)) {
            userRepository.findById(user.getId()).get().setLastName(lastName);
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Object> editPassword(String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String msg = Validator.passwordValidate(newPassword);
        if (msg.isEmpty()) {
            user.getAccount().setPassword(newPassword);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(msg);
        }
    }

    @Override
    public User findUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}