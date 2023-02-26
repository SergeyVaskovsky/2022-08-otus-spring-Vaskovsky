package ru.otus.poem.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.otus.poem.config.AppProps;
import ru.otus.poem.exception.TelegramApiRuntimeException;
import ru.otus.poem.model.dto.TelegramMessage;
import ru.otus.poem.service.TelegramBotService;

import java.util.List;


@Component
public class Bot extends TelegramLongPollingBot {

    private static final String ACCEPT = "accept_";
    private static final String DELETE = "delete_";
    private final AppProps appProps;
    private final TelegramBotService service;

    @Autowired
    public Bot(AppProps appProps, TelegramBotService service) throws TelegramApiException {
        this.appProps = appProps;
        this.service = service;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
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
        var callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null) {
            var data = callbackQuery.getData();
            if (data.startsWith(ACCEPT)) {
                var id = data.replace(ACCEPT, "");
                service.moderate(id);
                hideKeyboard(update);
            } else if (data.startsWith(DELETE)) {
                var id = data.replace(DELETE, "");
                service.delete(id);
                hideKeyboard(update);
            }
        }
    }

    private void hideKeyboard(Update update) {
        EditMessageReplyMarkup changedMessage = EditMessageReplyMarkup
                .builder()
                .chatId(appProps.getChatId())
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .replyMarkup(null)
                .build();
        try {
            execute(changedMessage);
        } catch (TelegramApiException e) {
            throw new TelegramApiRuntimeException(e);
        }
    }

    public void sendComment(TelegramMessage message) {
       /*var url = InlineKeyboardButton.builder()
                .text("Poem reference")
                .url(message.getReference())
                .build();*/
        var accept = InlineKeyboardButton.builder().text("Accept").callbackData(ACCEPT + message.getId()).build();
        var delete = InlineKeyboardButton.builder().text("Delete").callbackData(DELETE + message.getId()).build();

        var keyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(accept, delete))
                //.keyboardRow(List.of(url))
                .build();

        SendMessage sm = SendMessage.builder()
                .chatId(appProps.getChatId())
                .parseMode("HTML")
                .text(message.getText() + "\n" + message.getReference())
                .replyMarkup(keyboard).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new TelegramApiRuntimeException(e);
        }
    }

}
