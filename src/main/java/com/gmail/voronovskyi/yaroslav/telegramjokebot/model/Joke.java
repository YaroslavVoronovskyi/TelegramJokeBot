package com.gmail.voronovskyi.yaroslav.telegramjokebot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "jokes")
public class Joke {

    @Id
    private Long id;

    private String body;
    private String category;
    private Double rating;
}
