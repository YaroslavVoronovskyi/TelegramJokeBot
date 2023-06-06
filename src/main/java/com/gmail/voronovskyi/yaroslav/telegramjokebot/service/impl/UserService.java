package com.gmail.voronovskyi.yaroslav.telegramjokebot.service.impl;

import com.gmail.voronovskyi.yaroslav.telegramjokebot.repository.IUserRepository;
import com.gmail.voronovskyi.yaroslav.telegramjokebot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
