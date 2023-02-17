package ru.otus.poem.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.otus.poem.config.AppProps;
import ru.otus.poem.model.dto.TelegramMessage;

import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    private final AppProps appProps;
    private TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

    @Autowired
    public Bot(AppProps appProps) throws TelegramApiException {
        this.appProps = appProps;
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return appProps.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return appProps.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();
        System.out.println(user.getFirstName() + " wrote " + msg.getText() + " chat id " + id);
    }

    public void sendComment(TelegramMessage message) {
        var url = InlineKeyboardButton.builder()
                .text("Poem reference")
                .url(message.getReference())
                .build();
        var accept = InlineKeyboardButton.builder().text("Accept").callbackData("accept_" + message.getId()).build();
        var delete = InlineKeyboardButton.builder().text("Delete").callbackData("delete_" + message.getId()).build();

        var keyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(accept, delete))
                .keyboardRow(List.of(url))
                .build();

        SendMessage sm = SendMessage.builder().chatId(appProps.getChatId())
                .parseMode("HTML").text(message.getText())
                .replyMarkup(keyboard).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
