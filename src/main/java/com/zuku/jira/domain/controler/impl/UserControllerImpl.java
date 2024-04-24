package com.zuku.jira.domain.controler.impl;

import com.zuku.jira.domain.controler.IUserController;
import com.zuku.jira.domain.service.impl.UserServiceImpl;
import com.zuku.jira.entity.User;
import com.zuku.jira.helpers.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserControllerImpl implements IUserController {
    @Autowired
    private final UserServiceImpl userService;

    public UserControllerImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(path = "{userId}")
    @Override
    public User getUserById(@PathVariable("userId") Long userId) {
        return userService.findUser(userId);
    }

    @GetMapping
    @Override
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    @Override
    public ActionResult createUser(@RequestBody User user) {
        return userService.signUp(user);
    }

    @PutMapping(path = "/updateCurrentUser")
    @Override
    public ActionResult updateUser(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        return userService.editUserData(firstName, lastName);
    }

    @PutMapping(path = "/updateCurrentUserPassword")
    @Override
    public ActionResult updatePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        return userService.editPassword(oldPassword, newPassword);
    }

    @DeleteMapping
    @Override
    public ActionResult removeAccount() {
        return userService.unregister();
    }


    // zamienic na authetication !!!!!!
    @GetMapping("/signIn/{login}/{password}")
    @Override
    public ActionResult signIn(
            @PathVariable("login") String login,
            @PathVariable("password") String password) {
        return userService.signIn(login, password);
    }

    @GetMapping("/signOut")
    @Override
    public ActionResult signOut() {
        return userService.signOut();
    }
}

