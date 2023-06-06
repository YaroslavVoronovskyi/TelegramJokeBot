package com.gmail.voronovskyi.yaroslav.telegramjokebot.repository;

import com.gmail.voronovskyi.yaroslav.telegramjokebot.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJokeRepository extends JpaRepository<Joke, Integer> {
}
