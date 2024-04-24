package com.zuku.jira.domain.controler.impl;

import com.zuku.jira.JiraProjectSpringBootApplication;
import com.zuku.jira.domain.controler.IBoardController;
import com.zuku.jira.domain.service.IBoardService;
import com.zuku.jira.entity.Board;
import com.zuku.jira.helpers.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardControllerImpl implements IBoardController {
    private final IBoardService boardService;

    @Autowired
    public BoardControllerImpl(IBoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(path = "/id:{boardId}")
    @Override
    public Board getBoardById(@PathVariable("boardId") Long boardId){
        return boardService.findBoard(boardId);
    }

    @GetMapping(path = "/name:{boardName}")
    @Override
    public Board getBoardByName(@PathVariable("boardName") String name){
        return boardService.findByName(name);
    }

    @GetMapping
    @Override
    public List<Board> getAllBoards(){
         return boardService.findAllBoards();
    }

    @DeleteMapping(path = "{boardId}")
    @Override
    public ActionResult removeBoard(@PathVariable("boardId") Long boardId){
        return boardService.deleteBoard(boardId);
    }

    @PostMapping
    @Override
    public ActionResult registerNewBoard(@RequestBody Board board){
        System.out.println(board);
        return boardService.createBoard(board);
    }

    @PutMapping("{oldName}")
    @Override
    public ActionResult updateBoard(
            @PathVariable("oldName") String oldName,
            @RequestParam String newName) {
        return boardService.editBoard(oldName, newName);
    }
}