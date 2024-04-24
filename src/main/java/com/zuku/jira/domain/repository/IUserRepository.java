package com.zuku.jira.domain.repository;

import com.zuku.jira.entity.Account;
import com.zuku.jira.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    @Query("select u from User u where u.account = ?1")
    Optional<User> findByAccount(Account account);
}
