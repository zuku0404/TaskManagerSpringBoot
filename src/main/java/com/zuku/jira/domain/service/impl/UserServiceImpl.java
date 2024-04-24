package com.zuku.jira.domain.service.impl;

import com.zuku.jira.domain.cypher.IEncryptionService;
import com.zuku.jira.domain.repository.IAccountRepository;
import com.zuku.jira.domain.repository.IUserRepository;
import com.zuku.jira.domain.service.IUserService;
import com.zuku.jira.domain.validator.Validator;
import com.zuku.jira.entity.Account;
import com.zuku.jira.entity.User;
import com.zuku.jira.helpers.ActionResult;
import com.zuku.jira.helpers.ActionResultDescription;
import com.zuku.jira.helpers.CurrentUser;
import com.zuku.jira.helpers.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IAccountRepository accountRepository;
    private final IEncryptionService encryptionService;

    @Autowired
    public UserServiceImpl(IUserRepository repository, IAccountRepository accountRepository, IEncryptionService encryptionService) {
        this.userRepository = repository;
        this.accountRepository = accountRepository;
        this.encryptionService = encryptionService;
    }

    @Override
    public ActionResult signIn(String login, String password) {
        Optional<Account> accountOptional = accountRepository.findByLogin(login);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (Objects.equals(encryptionService.decrypt(account.getPassword()), password)) {
                Optional<User> userOptional = userRepository.findByAccount(account);
                if (userOptional.isPresent()) {
                    CurrentUser.getInstance().setUser(userOptional.get());
                    return new ActionResult(Status.SUCCESS, ActionResultDescription.SUCCESS.getDescription());
                } else {
                    return new ActionResult(Status.INVALID, ActionResultDescription.USER_NOT_EXIST.getDescription());
                }

            } else {
                return new ActionResult(Status.INVALID, ActionResultDescription.INCORRECT_PASSWORD.getDescription());
            }
        } else {
            return new ActionResult(Status.INVALID, ActionResultDescription.LOGIN_NOT_EXIST.getDescription());
        }
    }

    @Override
    public ActionResult signOut() {
        if (CurrentUser.getInstance().getUser() != null) {
            CurrentUser.getInstance().setUser(null);
            return new ActionResult(Status.SUCCESS, ActionResultDescription.SUCCESS.getDescription());
        } else {
            return new ActionResult(Status.INVALID, ActionResultDescription.NO_USER_LOGGED_IN.getDescription());
        }
    }

    @Override
    public ActionResult signUp(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Validator.loginValidate(user.getAccount().getLogin()));
        stringBuilder.append(Validator.passwordValidate(user.getAccount().getPassword()));
        if (!stringBuilder.isEmpty()) {
            return new ActionResult(Status.INVALID, stringBuilder.toString());
        } else {
            Optional<Account> accountOptional = accountRepository.findByLogin(user.getAccount().getLogin());
            if (accountOptional.isPresent()) {
                return new ActionResult(Status.INVALID, ActionResultDescription.LOGIN_EXIST.getDescription());
            } else {
                String encryptPassword = encryptionService.encrypt(user.getAccount().getPassword());
                accountRepository.save(new Account(user.getAccount().getLogin(), encryptPassword));
                Optional<Account> account = accountRepository.findByLogin(user.getAccount().getLogin());
                if (account.isPresent()) {
                    userRepository.save(new User(user.getName(), user.getLastName(), account.get()));
                    return new ActionResult(Status.SUCCESS, ActionResultDescription.SUCCESS.getDescription());
                } else {
                    return new ActionResult(Status.INVALID, ActionResultDescription.FAIL_SAVE_CHANGES_DB.getDescription());
                }
            }
        }
    }

    @Override
    public ActionResult unregister() {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null) {
            userRepository.deleteById(currentUser.getId());
            CurrentUser.getInstance().setUser(null);
            return new ActionResult(Status.SUCCESS, ActionResultDescription.SUCCESS.getDescription());
        } else {
            return new ActionResult(Status.INVALID, ActionResultDescription.NO_USER_LOGGED_IN.getDescription());
        }
    }

    @Override
    @Transactional
    public ActionResult editUserData(String firstName, String lastName) {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null) {
            if (firstName != null && !firstName.isBlank() && Objects.equals(currentUser.getName(), firstName)) {
                currentUser.setName(firstName);
            }
            if (lastName != null && !lastName.isBlank() && Objects.equals(currentUser.getLastName(), lastName)) {
                currentUser.setLastName(lastName);
            }
            return new ActionResult(Status.SUCCESS, ActionResultDescription.SUCCESS.getDescription());
        } else {
            return new ActionResult(Status.INVALID, ActionResultDescription.NO_USER_LOGGED_IN.getDescription());
        }
    }

    @Override
    @Transactional
    public ActionResult editPassword(String oldPassword, String newPassword) {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null) {
            if (!Objects.equals(encryptionService.decrypt(currentUser.getAccount().getPassword()), oldPassword)) {
                return new ActionResult(Status.INVALID, ActionResultDescription.INCORRECT_PASSWORD.getDescription());
            } else {
                String msg = Validator.passwordValidate(newPassword);
                if (msg.isEmpty()) {
                    currentUser.getAccount().setPassword(newPassword);
                    return new ActionResult(Status.SUCCESS, ActionResultDescription.SUCCESS.getDescription());
                } else {
                    return new ActionResult(Status.INVALID, msg);
                }
            }
        } else {
            return new ActionResult(Status.INVALID, ActionResultDescription.NO_USER_LOGGED_IN.getDescription());
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