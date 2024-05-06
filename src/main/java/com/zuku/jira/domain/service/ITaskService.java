package com.zuku.jira.domain.service;

import com.zuku.jira.dto.TaskDto;
import com.zuku.jira.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITaskService {
    public ResponseEntity<Object> createTask(TaskDto task);

    @Transactional
    ResponseEntity<Object> editTask(Long taskId, TaskDto taskDto);

    ResponseEntity<Object> removeTask(Long taskId);
    public Task findTaskByTitle(String title);
    public List<Task> findTasksForBoard(Long boardId);
    public List<Task> findTasksForUser(Long userId);
    public List<Task> findTaskForCurrentUser();
    public Task findTaskById(Long taskId);
    public List<Task> findAllTasks();

}
