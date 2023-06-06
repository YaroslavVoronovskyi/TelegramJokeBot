package com.gmail.voronovskyi.yaroslav.telegramjokebot.repository;

import com.gmail.voronovskyi.yaroslav.telegramjokebot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
}
