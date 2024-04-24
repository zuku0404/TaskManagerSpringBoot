package com.zuku.jira.domain.controler;

import com.zuku.jira.entity.Board;
import com.zuku.jira.helpers.ActionResult;

import java.util.List;

public interface IBoardController {
    public Board getBoardById(Long boardId);

    public Board getBoardByName(String name);

    public List<Board> getAllBoards();

    public ActionResult removeBoard(Long boardId);
    public ActionResult registerNewBoard(Board board);

    public ActionResult updateBoard(String oldName, String newName);
}