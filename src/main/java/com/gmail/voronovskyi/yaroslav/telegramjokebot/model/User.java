package com.gmail.voronovskyi.yaroslav.telegramjokebot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "users")
public class User {

    @Id
    private Long chatId;

    private Boolean embedeJoke;
    private String phoneNumber;
    private LocalDateTime registeredDate;
    private String firstName;
    private String lastName;
    private String userName;
    private Double latitude;
    private Double longitude;
    private String bio;
    private String description;
    private String pinnedMessage;
}
