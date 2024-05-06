package com.zuku.jira.domain.service;

import com.zuku.jira.entity.Board;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBoardService {
    public ResponseEntity<Object> createBoard(Board board);

    public ResponseEntity<Object> editBoard(Long boardId, String newName);

    public ResponseEntity<Object> deleteBoard(Long id);

    public Board findBoardById(Long id);

    public Board findBoardByName(String name);

    public List<Board> findBoards();
}
