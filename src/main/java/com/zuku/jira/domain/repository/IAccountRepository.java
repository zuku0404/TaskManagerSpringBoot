package com.zuku.jira.domain.repository;

import com.zuku.jira.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE login = ?1")
    Optional<Account> findByLogin(String login);
}
