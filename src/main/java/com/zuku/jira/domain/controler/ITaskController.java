package com.zuku.jira.domain.controler;

import com.zuku.jira.dto.TaskDto;
import com.zuku.jira.entity.Task;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITaskController {
    public ResponseEntity<Object> createNewTask(TaskDto task);

    public ResponseEntity<Object> updateTask(Long taskId, String title, String description, Long boardId, Long userId);

    public ResponseEntity<Object> removeTask(Long taskId);

    public Task getTaskByTitle(String title);

    public Task getTaskById(Long taskId);

    public List<Task> getTasksForCurrentUser();

    public List<Task> getTasksForBoard(Long boardId);

    public List<Task> getTasksForUser(Long userId);

    public List<Task> getAllTasks();
}