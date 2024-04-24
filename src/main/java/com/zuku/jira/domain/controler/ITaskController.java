package com.zuku.jira.domain.controler;

import com.zuku.jira.entity.Board;
import com.zuku.jira.entity.Task;
import com.zuku.jira.entity.User;
import com.zuku.jira.helpers.ActionResult;

import java.util.List;

public interface ITaskController {
    public ActionResult createNewTask(Task task);

    public ActionResult updateTask(Long taskId, String title, String description, Long boardId, Long userId);

    public ActionResult removeTask(Long taskId);

    public Task getTaskByTitle(String title);

    public Task getTaskById(Long taskId);

    public List<Task> getTasksForCurrentUser();

    public List<Task> getTasksForBoard(Board board);

    public List<Task> getTasksForUser(User user);

    public List<Task> getAllTasks();
}