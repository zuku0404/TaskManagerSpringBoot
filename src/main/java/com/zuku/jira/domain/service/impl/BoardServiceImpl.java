package com.zuku.jira.domain.service.impl;

import com.zuku.jira.domain.repository.IBoardRepository;
import com.zuku.jira.domain.repository.ITaskRepository;
import com.zuku.jira.domain.service.IBoardService;
import com.zuku.jira.domain.validator.Validator;
import com.zuku.jira.entity.Board;
import com.zuku.jira.domain.enums.ActionResultDescription;
import com.zuku.jira.entity.Task;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements IBoardService {
    private final IBoardRepository boardRepository;
    private final ITaskRepository taskRepository;

    @Override
    public ResponseEntity<Object> createBoard(Board board) {
        String name = board.getName();
        String trimmedName = name.trim();
        String resultOfValidation = Validator.boardValidator(trimmedName);
        if (resultOfValidation.isEmpty()) {
            Optional<Board> boardOptional = boardRepository.findByName(name);
            if (boardOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ActionResultDescription.BOARD_NAME_EXIST.getDescription());
            } else {
                boardRepository.save(board);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(board);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultOfValidation);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> editBoard(Long boardId, String newName) {
        String resultOfValidation = Validator.boardValidator(newName);
        if (resultOfValidation.isEmpty()) {
            Optional<Board> boardOptional = boardRepository.findById(boardId);
            if (boardOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ActionResultDescription.BOARD_NOT_EXIST.getDescription());
            }
            Board board = boardOptional.get();
            board.setName(newName);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultOfValidation);
        }
    }

    @Override
    public ResponseEntity<Object> deleteBoard(Long boardId) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ActionResultDescription.BOARD_NOT_EXIST.getDescription());
        }
        List<Task> allByBoardId = taskRepository.findAllByBoardId(boardId);
        taskRepository.deleteAll(allByBoardId);
        boardRepository.deleteById(boardId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public Board findBoardByName(String name) {
        return boardRepository.findByName(name).orElse(null);
    }

    @Override
    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElse(null);
    }

    @Override
    public List<Board> findBoards() {
        return boardRepository.findAll();
    }
}
