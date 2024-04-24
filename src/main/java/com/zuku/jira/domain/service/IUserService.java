package com.zuku.jira.domain.service;

import com.zuku.jira.entity.User;
import com.zuku.jira.helpers.ActionResult;

import java.util.List;

public interface IUserService {
    public ActionResult signIn(String login, String password);
    public ActionResult signOut();
    public ActionResult signUp(User user);
    public ActionResult unregister();
    public ActionResult editUserData(String firstName,String lastName);
    public ActionResult editPassword(String oldPassword, String newPassword);
    public User findUser(Long userId);
    public List<User> findAllUsers();
}
