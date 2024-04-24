package com.zuku.jira.domain.service.impl;

import com.zuku.jira.domain.repository.IBoardRepository;
import com.zuku.jira.domain.repository.ITaskRepository;
import com.zuku.jira.domain.repository.IUserRepository;
import com.zuku.jira.domain.service.ITaskService;
import com.zuku.jira.entity.Board;
import com.zuku.jira.entity.Task;
import com.zuku.jira.entity.User;
import com.zuku.jira.helpers.ActionResult;
import com.zuku.jira.helpers.ActionResultDescription;
import com.zuku.jira.helpers.CurrentUser;
import com.zuku.jira.helpers.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements ITaskService {
    private final ITaskRepository taskRepository;
    private final IBoardRepository boardRepository;
    private final IUserRepository userRepository;

    @Autowired
    public TaskServiceImpl(ITaskRepository taskRepository, IBoardRepository boardRepository, IUserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ActionResult createTask(Task task) {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null) {
            if (taskRepository.findByTitle(task.getTitle()).isEmpty()) {
                Optional<Board> board = boardRepository.findById(task.getBoard().getId());
                if (board.isPresent()) {
                    User user = userRepository.findById(task.getUser().getId()).orElse(null);
                    taskRepository.save(new Task(task.getTitle(), task.getDescription(), user, board.get()));
                    return new ActionResult(Status.SUCCESS, ActionResultDescription.SUCCESS.getDescription());
                } else {
                    return new ActionResult(Status.INVALID, ActionResultDescription.BOARD_NOT_EXIST.getDescription());
                }
            } else {
                return new ActionResult(Status.INVALID, ActionResultDescription.TASK_NAME_EXIST.getDescription());
            }
        } else {
            return new ActionResult(Status.INVALID, ActionResultDescription.NO_USER_LOGGED_IN.getDescription());
        }
    }
    @Override
    @Transactional
    public ActionResult editTask(Long taskId, String title, String description, Long boardId, Long userID) {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null) {
            Optional<Task> taskOptional = taskRepository.findById(taskId);
            if (taskOptional.isPresent()) {
                Optional<Board> boardOptional = boardRepository.findById(boardId);
                if (boardOptional.isPresent()) {
                    Task task = taskOptional.get();
                    if (title != null && !title.isBlank() && !Objects.equals(task.getTitle(), title)) {
                        task.setTitle(title);
                    }
                    if (description != null && !description.isBlank() && !Objects.equals(task.getDescription(), description)) {
                        task.setDescription(description);
                    }
                    if (!Objects.equals(task.getBoard().getId(), boardId)) {
                        task.setBoard(boardOptional.get());
                    }
                    User user = userRepository.findById(userID).orElse(null);
                    if (!task.getUser().equals(user)) {
                        task.setUser(user);
                    }
                    return new ActionResult(Status.SUCCESS);
                } else {
                    return new ActionResult(Status.INVALID, ActionResultDescription.BOARD_NOT_EXIST.getDescription());
                }
            } else {
                return new ActionResult(Status.INVALID, ActionResultDescription.TASK_NOT_EXIST.getDescription());
            }
        } else {
            return new ActionResult(Status.INVALID, ActionResultDescription.NO_USER_LOGGED_IN.getDescription());
        }
    }
    @Override
    public ActionResult removeTask(Long taskId) {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null) {
            Optional<Task> taskOptional = taskRepository.findById(taskId);
            if (taskOptional.isPresent()) {
                taskRepository.deleteById(taskId);
                return new ActionResult(Status.SUCCESS, ActionResultDescription.SUCCESS.getDescription());
            } else {
                return new ActionResult(Status.INVALID, ActionResultDescription.TASK_NOT_EXIST.getDescription());
            }
        } else {
            return new ActionResult(Status.INVALID, ActionResultDescription.NO_USER_LOGGED_IN.getDescription());
        }
    }

    @Override
    public Task findTaskByTitle(String title) {
        return taskRepository.findByTitle(title).orElse(null);
    }

    @Override
    public List<Task> findTasksForBoard(Board board) {
        return taskRepository.findAllByBoardId(board.getId());
    }
    @Override
    public List<Task> findTasksForUser(User user) {
        return taskRepository.findAllByUserId(user.getId());
    }
    @Override
    public List<Task> findTaskForCurrentUser() {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null) {
            return taskRepository.findAllByUserId(currentUser.getId());
        }
        return new ArrayList<>();
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
