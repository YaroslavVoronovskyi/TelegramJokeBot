package com.gmail.voronovskyi.yaroslav.telegramjokebot;

public class Constants {

    public static final String HELP_TEXT = "This bot is created to send a random joke from the database each time you request it. \n\n" +
            "You can execute commands from the main menu on the left or by typing command manually: \n\n" +
            "Type /start to see a welcome message \n\n" +
            "Type /joke to get a random joke \n\n" +
            "Type /help to list available settings to configure \n\n" +
            "Type /settings to see this message again \n\n";
    public static final Integer MAX_JOKE_ID_MINUS_ONE = 3772;
    public static final String NEXT_JOKE = "NEXT_JOKE";
    public static final String ERROR_MESSAGE = "Error occurred: {}";
    public static final String COMMAND_NOT_FOUND_MESSAGE = "Command not recognized, please verify and try again " +
            ":stuck_out_tongue_winking_eye: ";
}
