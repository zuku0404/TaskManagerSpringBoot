package com.zuku.jira.domain.controler.impl;

import com.zuku.jira.domain.controler.ITaskController;
import com.zuku.jira.domain.service.ITaskService;
import com.zuku.jira.dto.TaskDto;
import com.zuku.jira.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskControllerImpl implements ITaskController {
    private final ITaskService taskService;

    @GetMapping(path = "/name/{title}")
    @Override
    public Task getTaskByTitle(@PathVariable("title") String title) {
        return taskService.findTaskByTitle(title);
    }

    @GetMapping(path = "/{taskId}")
    @Override
    public Task getTaskById(@PathVariable("taskId") Long taskId) {
        return taskService.findTaskById(taskId);
    }

    @GetMapping(path = "/me")
    @Override
    public List<Task> getTasksForCurrentUser() {
        return taskService.findTaskForCurrentUser();
    }

    @GetMapping(path = "/board/{boardId}")
    @Override
    public List<Task> getTasksForBoard(@PathVariable("boardId") Long boardId) {
        return taskService.findTasksForBoard(boardId);
    }

    @GetMapping(path = "/user/{userId}")
    @Override
    public List<Task> getTasksForUser(@PathVariable("userId") Long userId) {
        return taskService.findTasksForUser(userId);
    }

    @GetMapping
    @Override
    public List<Task> getAllTasks() {
        return taskService.findAllTasks();
    }

    @PostMapping
    @Override
    public ResponseEntity<Object> createNewTask(@RequestBody TaskDto task) {
        return taskService.createTask(task);
    }

    @PutMapping(path = "/{taskId}")
    @Override
    public ResponseEntity<Object> updateTask(
            @PathVariable("taskId") Long taskId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long boardId,
            @RequestParam(required = false) Long userId) {
        return taskService.editTask(taskId, new TaskDto(title, description, boardId, userId));
    }

    @DeleteMapping(path = "/{taskId}")
    @Override
    public ResponseEntity<Object> removeTask(@PathVariable("taskId") Long taskId) {
        return taskService.removeTask(taskId);
    }


}