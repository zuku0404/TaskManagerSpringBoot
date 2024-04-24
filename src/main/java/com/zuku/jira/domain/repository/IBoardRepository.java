package com.zuku.jira.domain.repository;

import com.zuku.jira.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IBoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b where b.name = ?1")
    Optional<Board> findByName(String name);
}
