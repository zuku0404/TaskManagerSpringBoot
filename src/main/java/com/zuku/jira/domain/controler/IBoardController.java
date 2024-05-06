package com.zuku.jira.domain.controler;

import com.zuku.jira.entity.Board;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBoardController {
    public Board getBoardById(Long boardId);

    public Board getBoardByName(String name);

    public List<Board> getAllBoards();

    public ResponseEntity<Object> removeBoard(Long boardId);
    public ResponseEntity<Object> registerNewBoard(Board board);

    public ResponseEntity<Object> updateBoard(Long boardId, String newName);
}