package com.zuku.jira.domain.service.impl;

import com.zuku.jira.domain.repository.IBoardRepository;
import com.zuku.jira.domain.repository.ITaskRepository;
import com.zuku.jira.domain.repository.IUserRepository;
import com.zuku.jira.domain.service.ITaskService;
import com.zuku.jira.dto.TaskDto;
import com.zuku.jira.entity.Board;
import com.zuku.jira.entity.Task;
import com.zuku.jira.entity.User;
import com.zuku.jira.domain.enums.ActionResultDescription;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {
    private final ITaskRepository taskRepository;
    private final IBoardRepository boardRepository;
    private final IUserRepository userRepository;

    @Override
    public ResponseEntity<Object> createTask(TaskDto task) {
        if (taskRepository.findByTitle(task.getTitle()).isEmpty()) {
            if (task.getBoardId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ActionResultDescription.BOARD_NOT_EXIST.getDescription());
            }
            Optional<Board> boardOptional = boardRepository.findById(task.getBoardId());
            if (boardOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ActionResultDescription.BOARD_NOT_EXIST.getDescription());
            }
            Board board = boardOptional.get();
            User user = task.getUserID() != null ? userRepository.findById(task.getUserID()).orElse(null) : null;
            taskRepository.save(new Task(task.getTitle(), task.getDescription(), user, board));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(task);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ActionResultDescription.TASK_NAME_EXIST.getDescription());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> editTask(Long taskId, TaskDto taskDto) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ActionResultDescription.TASK_NOT_EXIST.getDescription());
        }
        Task task = taskOptional.get();
        Optional<Board> boardOptional = Optional.empty();
        if (taskDto.getBoardId() != null) {
            boardOptional = boardRepository.findById(taskDto.getBoardId());
            if (boardOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ActionResultDescription.BOARD_NOT_EXIST.getDescription());
            }
        }
        Board board = boardOptional.orElse(null);
        if (taskDto.getTitle() != null && !taskDto.getTitle().isBlank() && !Objects.equals(task.getTitle(), taskDto.getTitle())) {
            task.setTitle(taskDto.getTitle());
        }
        if (taskDto.getDescription() != null && !taskDto.getDescription().isBlank() && !Objects.equals(task.getDescription(), taskDto.getDescription())) {
            task.setDescription(taskDto.getDescription());
        }
        if (taskDto.getBoardId() != null && !Objects.equals(task.getBoard().getId(), taskDto.getBoardId())) {
            task.setBoard(board);
        }
        User user = taskDto.getUserID() != null ? userRepository.findById(taskDto.getUserID()).orElse(null) : null;
        if (task.getUser() != null && !task.getUser().equals(user) || task.getUser() == null && user != null) {
            task.setUser(user);
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Object> removeTask(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ActionResultDescription.TASK_NOT_EXIST.getDescription());
        }
        taskRepository.deleteById(taskId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public Task findTaskByTitle(String title) {
        return taskRepository.findByTitle(title).orElse(null);
    }

    @Override
    public List<Task> findTasksForBoard(Long boardId) {
        return taskRepository.findAllByBoardId(boardId);
    }

    @Override
    public List<Task> findTasksForUser(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    public List<Task> findTaskForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return taskRepository.findAllByUserId(user.getId());
    }

    @Override
    public Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
}
