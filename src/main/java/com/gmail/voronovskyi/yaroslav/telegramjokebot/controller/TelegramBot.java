package com.gmail.voronovskyi.yaroslav.telegramjokebot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.gmail.voronovskyi.yaroslav.telegramjokebot.Constants;
import com.gmail.voronovskyi.yaroslav.telegramjokebot.config.BotConfig;
import com.gmail.voronovskyi.yaroslav.telegramjokebot.model.Joke;
import com.gmail.voronovskyi.yaroslav.telegramjokebot.service.IJokeService;
import com.gmail.voronovskyi.yaroslav.telegramjokebot.service.IUserService;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final IUserService userService;
    private final IJokeService jokeService;

    @Autowired
    public TelegramBot(BotConfig botConfig, IUserService userService, IJokeService jokeService) {
        this.botConfig = botConfig;
        this.userService = userService;
        this.jokeService = jokeService;
        List<BotCommand> botCommandsList = new ArrayList<>();
        botCommandsList.add(new BotCommand("/start", "get a welcome message"));
        botCommandsList.add(new BotCommand("/joke", "get a random joke"));
        botCommandsList.add(new BotCommand("/help", "info how to use this bot"));
        botCommandsList.add(new BotCommand("/settings", "set your preferences"));
        try {
            this.execute(new SetMyCommands(botCommandsList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException exception) {
            log.error(Arrays.toString(exception.getStackTrace()));
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> {
                    showStart(chatId, update.getMessage().getChat().getFirstName());
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        TypeFactory typeFactory = objectMapper.getTypeFactory();
                        List<Joke> jokesList = objectMapper.readValue(new File("db/stupidstuff.json"),
                                typeFactory.constructCollectionType(List.class, Joke.class));
                        jokeService.saveAll(jokesList);
                    } catch (Exception exception) {
                        log.error(Arrays.toString(exception.getStackTrace()));
                    }
                }
                case "/joke" -> {
                    var joke = getRandomJoke();
                    joke.ifPresent(randomJoke -> addButtonAndSendMessage(randomJoke.getBody(), chatId));
                }
                default -> commandNotFound(chatId);
            }
        } else if (update.hasCallbackQuery()) {

            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals(Constants.NEXT_JOKE)) {

                var joke = getRandomJoke();

                joke.ifPresent(randomJoke -> addButtonAndSendMessage(randomJoke.getBody(), chatId));
            }
        }
    }

    private void commandNotFound(long chatId) {
        String answer = EmojiParser.parseToUnicode(Constants.COMMAND_NOT_FOUND_MESSAGE);
        sendMessage(answer, chatId);
    }

    private void sendMessage(String answer, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(answer);
        send(message);
    }

    private void addButtonAndSendMessage(String joke, long chatId) {
        SendMessage message = new SendMessage();
        message.setText(joke);
        message.setChatId(chatId);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setCallbackData(Constants.NEXT_JOKE);
        inlineKeyboardButton.setText(EmojiParser.parseToUnicode("next joke " + ":rolling_on_the_floor_laughing:"));
        rowInLine.add(inlineKeyboardButton);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        message.setReplyMarkup(inlineKeyboardMarkup);
        send(message);
    }

    private void send(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException exception) {
            log.error(Arrays.toString(exception.getStackTrace()));
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    private void showStart(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode(
                "Hi, " + name + "! :smile:" + " Nice to meet you! I am a Simple Random Joke Bot created by " +
                        "Yaroslav Voronovskyi \n");
        sendMessage(answer, chatId);
    }

    private Optional<Joke> getRandomJoke() {
        var random = new Random();
        var randomId = random.nextInt(Constants.MAX_JOKE_ID_MINUS_ONE) + 1;
        return jokeService.findById(randomId);
    }
}
