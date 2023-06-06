package com.gmail.voronovskyi.yaroslav.telegramjokebot.service.impl;

import com.gmail.voronovskyi.yaroslav.telegramjokebot.repository.IJokeRepository;
import com.gmail.voronovskyi.yaroslav.telegramjokebot.service.IJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JokeService implements IJokeService {

    private final IJokeRepository jokeRepository;

    @Autowired
    public JokeService(IJokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }
}
