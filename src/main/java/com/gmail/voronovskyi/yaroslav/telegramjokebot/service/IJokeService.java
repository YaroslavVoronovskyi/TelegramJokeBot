package com.gmail.voronovskyi.yaroslav.telegramjokebot.service;

import com.gmail.voronovskyi.yaroslav.telegramjokebot.model.Joke;

import java.util.List;
import java.util.Optional;

public interface IJokeService {

    Optional<Joke> findById(int randomId);

    void saveAll(List<Joke> jokesList);
}
