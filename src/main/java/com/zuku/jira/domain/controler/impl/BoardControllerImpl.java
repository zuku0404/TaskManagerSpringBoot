package com.zuku.jira.domain.controler.impl;

import com.zuku.jira.domain.controler.IBoardController;
import com.zuku.jira.domain.service.IBoardService;
import com.zuku.jira.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardControllerImpl implements IBoardController {
    private final IBoardService boardService;

    @GetMapping(path = "/{boardId}")
    @Override
    public Board getBoardById(@PathVariable("boardId") Long boardId) {
        return boardService.findBoardById(boardId);
    }

    @GetMapping(path = "/name/{boardName}")
    @Override
    public Board getBoardByName(@PathVariable("boardName") String name) {
        return boardService.findBoardByName(name);
    }

    @GetMapping
    @Override
    public List<Board> getAllBoards() {
        return boardService.findBoards();
    }

    @PostMapping
    @Override
    public ResponseEntity<Object> registerNewBoard(@RequestBody Board board) {
        return boardService.createBoard(board);
    }

    @PutMapping("/{boardId}")
    @Override
    public ResponseEntity<Object> updateBoard(
            @PathVariable("boardId") Long boardId,
            @RequestParam String name) {
        return boardService.editBoard(boardId, name);
    }

    @DeleteMapping(path = "/{boardId}")
    @Override
    public ResponseEntity<Object> removeBoard(@PathVariable("boardId") Long boardId) {
        return boardService.deleteBoard(boardId);
    }
}