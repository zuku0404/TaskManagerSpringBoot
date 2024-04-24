package com.zuku.jira.domain.service.impl;

import com.zuku.jira.domain.repository.IBoardRepository;
import com.zuku.jira.domain.service.IBoardService;
import com.zuku.jira.domain.validator.Validator;
import com.zuku.jira.entity.Board;
import com.zuku.jira.entity.User;
import com.zuku.jira.helpers.ActionResult;
import com.zuku.jira.helpers.ActionResultDescription;
import com.zuku.jira.helpers.CurrentUser;
import com.zuku.jira.helpers.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements IBoardService {
    private IBoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public ActionResult createBoard(Board board) {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null) {
            String name = board.getName();
            String trimmedName = name.trim();
            String resultOfValidation = Validator.boardValidator(trimmedName);
            if (resultOfValidation.isEmpty()) {
                Optional<Board> boardOptional = boardRepository.findByName(name);
                if (boardOptional.isPresent()) {
                    return new ActionResult(Status.INVALID, ActionResultDescription.BOARD_NAME_EXIST.getDescription());
                } else {
                    boardRepository.save(new Board(trimmedName));
                    return new ActionResult(Status.SUCCESS, ActionResultDescription.SUCCESS.getDescription());
                }
            } else {
                return new ActionResult(Status.INVALID, resultOfValidation);
            }
        } else {
            return new ActionResult(Status.INVALID, ActionResultDescription.NO_USER_LOGGED_IN.getDescription());
        }
    }

    @Override
    @Transactional
    public ActionResult editBoard(String oldName, String newName) {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null) {
            String resultOfValidation = Validator.boardValidator(newName);
            if (resultOfValidation.isEmpty()) {
                Optional<Board> boardOptional = boardRepository.findByName(oldName);
                if (boardOptional.isPresent()) {
                    Board board = boardOptional.get();
                    board.setName(newName);
                    return new ActionResult(Status.SUCCESS, ActionResultDescription.SUCCESS.getDescription());
                } else {
                    return new ActionResult(Status.INVALID, ActionResultDescription.BOARD_NOT_EXIST.getDescription());
                }
            } else {
                return new ActionResult(Status.INVALID, resultOfValidation);
            }
        } else {
            return new ActionResult(Status.INVALID, ActionResultDescription.NO_USER_LOGGED_IN.getDescription());
        }
    }

    @Override
    public ActionResult deleteBoard(Long boardId) {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null) {
            if (boardRepository.findById(boardId).isPresent()) {
                boardRepository.deleteById(boardId);
                return new ActionResult(Status.SUCCESS, ActionResultDescription.SUCCESS.getDescription());
            } else {
                return new ActionResult(Status.INVALID, ActionResultDescription.BOARD_NOT_EXIST.getDescription());
            }
        } else {
            return new ActionResult(Status.INVALID, ActionResultDescription.NO_USER_LOGGED_IN.getDescription());
        }
    }

    @Override
    public Board findByName(String name) {
        return boardRepository.findByName(name).orElse(null);
    }

    @Override
    public Board findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElse(null);
    }

    @Override
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }
}
