package lv.telegrambot;

import org.altervista.leocus.telegrambotutilities.TelegramBot;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SsAdTelegramBot {

    private static final String botToken = "";
    private static final String chatId = "";
    private static final String greeting = "Hello! This is whats up!";
    private static final String farewell = "Hope this helps, Bye!";

    public void sendMessages(List<String> ssLinks) throws IOException {
        TelegramBot bot = new TelegramBot(botToken);

        if(!ssLinks.isEmpty()) {
            bot.sendMessage(chatId, greeting);
            for (String link : ssLinks) {
                bot.sendMessage(chatId, "" + link);
            }
            bot.sendMessage(chatId, farewell);
        }
    }

    public void sendMessagesFromFile(String fileName) throws IOException {
        List<String> ssLinks = FileUtils.readLines(new File(fileName));
        sendMessages(ssLinks);
    }
}
