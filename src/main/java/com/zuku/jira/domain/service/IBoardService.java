package com.zuku.jira.domain.service;

import com.zuku.jira.entity.Board;
import com.zuku.jira.helpers.ActionResult;

import java.util.List;

public interface IBoardService {
    public ActionResult createBoard(Board board);
    public  ActionResult editBoard(String oldName,String newName);
    public ActionResult deleteBoard(Long id);
    public Board findBoard(Long id);

    public Board findByName(String name);
    public List<Board> findAllBoards();
}
