package com.zuku.jira.domain.controler.impl;

import com.zuku.jira.domain.controler.ITaskController;
import com.zuku.jira.domain.service.ITaskService;
import com.zuku.jira.entity.Board;
import com.zuku.jira.entity.Task;
import com.zuku.jira.entity.User;
import com.zuku.jira.helpers.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")

public class TaskControllerImpl implements ITaskController {

    private final ITaskService taskService;

    @Autowired
    public TaskControllerImpl(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Override
    public ActionResult createNewTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PostMapping(path = "{taskId}")
    @Override
    public ActionResult updateTask(
            @PathVariable("taskId") Long taskId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long boardId,
            @RequestParam(required = false) Long userId) {
        return taskService.editTask(taskId, title, description, boardId, userId);
    }

    @DeleteMapping(path = "{taskId}")
    @Override
    public ActionResult removeTask(@PathVariable("taskId") Long taskId) {
        return taskService.removeTask(taskId);
    }

    @GetMapping(path = "{title}")
    @Override
    public Task getTaskByTitle(@PathVariable("title") String title) {
        return taskService.findTaskByTitle(title);
    }

    @GetMapping(path = "{taskId}")
    @Override
    public Task getTaskById(@PathVariable("taskId") Long taskId) {
        return taskService.findTaskById(taskId);
    }

    @GetMapping(path = "/currentUserTask")
    @Override
    public List<Task> getTasksForCurrentUser() {
        return taskService.findTaskForCurrentUser();
    }

    @GetMapping(path = "{board}")
    @Override
    public List<Task> getTasksForBoard(@PathVariable("board") Board board) {
        return taskService.findTasksForBoard(board);
    }

    @GetMapping(path = "{user}")
    @Override
    public List<Task> getTasksForUser(@PathVariable("user") User user) {
        return taskService.findTasksForUser(user);
    }

    @GetMapping
    @Override
    public List<Task> getAllTasks() {
        return taskService.findAllTasks();
    }
}