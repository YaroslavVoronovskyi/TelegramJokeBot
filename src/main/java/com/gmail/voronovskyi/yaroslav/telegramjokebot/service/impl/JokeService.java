package com.gmail.voronovskyi.yaroslav.telegramjokebot.service.impl;

import com.gmail.voronovskyi.yaroslav.telegramjokebot.model.Joke;
import com.gmail.voronovskyi.yaroslav.telegramjokebot.repository.IJokeRepository;
import com.gmail.voronovskyi.yaroslav.telegramjokebot.service.IJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JokeService implements IJokeService {

    private final IJokeRepository jokeRepository;

    @Autowired
    public JokeService(IJokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    @Override
    public Optional<Joke> findById(int randomId) {
        return jokeRepository.findById(randomId);
    }

    @Override
    public void saveAll(List<Joke> jokesList) {
        jokeRepository.saveAll(jokesList);
    }
}
