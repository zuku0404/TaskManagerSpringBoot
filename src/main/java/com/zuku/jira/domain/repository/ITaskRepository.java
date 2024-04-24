package com.zuku.jira.domain.repository;

import com.zuku.jira.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    @Query("select t from Task t where t.title = ?1")
    Optional<Task> findByTitle(String title);

    @Query("select t from Task t where t.board.id = ?1")
    List<Task> findAllByBoardId(Long boardId);

    @Query("select t from Task t where t.user.id = ?1")
    List<Task> findAllByUserId(Long userId);
}
