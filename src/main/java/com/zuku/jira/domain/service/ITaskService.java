package com.zuku.jira.domain.service;

import com.zuku.jira.entity.Board;
import com.zuku.jira.entity.Task;
import com.zuku.jira.entity.User;
import com.zuku.jira.helpers.ActionResult;

import java.util.List;

public interface ITaskService {
    public ActionResult createTask(Task task);
    public ActionResult editTask(Long taskId, String title, String description, Long boardId, Long userID);
    ActionResult removeTask(Long taskId);
    public Task findTaskByTitle(String title);
    public List<Task> findTasksForBoard(Board board);
    public List<Task> findTasksForUser(User user);
    public List<Task> findTaskForCurrentUser();
    public Task findTaskById(Long taskId);
    public List<Task> findAllTasks();

}
