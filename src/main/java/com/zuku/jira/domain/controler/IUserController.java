package com.zuku.jira.domain.controler;

import com.zuku.jira.entity.User;
import com.zuku.jira.helpers.ActionResult;

import java.util.List;

public interface IUserController {
    public User getUserById(Long userId);

    public List<User> getUsers();

    public ActionResult createUser(User user);

    public ActionResult updateUser(String firstName, String lastName);

    public ActionResult updatePassword(String oldPassword, String newPassword);

    public ActionResult removeAccount();

    public ActionResult signIn(String login, String password);

    public ActionResult signOut();
}