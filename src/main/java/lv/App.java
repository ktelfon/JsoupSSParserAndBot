package lv;

import lv.parser.SsLvParser;
import lv.parser.objects.ParseResults;
import lv.telegrambot.SsAdTelegramBot;
import org.altervista.leocus.telegrambotutilities.*;
import java.io.IOException;

public class App
{

    public static void main( String[] args ) throws IOException {

        SsLvParser p = new SsLvParser();
        ParseResults pr = p.getAppartamentRentOptions(p.buildSearchParams());

        SsAdTelegramBot bot = new SsAdTelegramBot();
        bot.sendMessages(pr.getLinks());
    }

}
