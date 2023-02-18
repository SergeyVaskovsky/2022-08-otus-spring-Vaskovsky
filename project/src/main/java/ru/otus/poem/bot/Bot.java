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
import ru.otus.poem.model.dto.CommentDto;
import ru.otus.poem.model.dto.TelegramMessage;
import ru.otus.poem.service.CommentService;

import java.util.List;


@Component
public class Bot extends TelegramLongPollingBot {

    private final AppProps appProps;
    private final CommentService commentService;

    @Autowired
    public Bot(AppProps appProps, CommentService commentService) throws TelegramApiException {
        this.appProps = appProps;
        this.commentService = commentService;
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
            if (data.startsWith("accept_")) {
                var id = data.replace("accept_", "");
                CommentDto commentDto = commentService.getById(Long.valueOf(id));
                CommentDto commentDtoToSave = new CommentDto(
                        commentDto.getId(),
                        commentDto.getText(),
                        commentDto.getUser(),
                        commentDto.getPoem(),
                        commentDto.getRootComment(),
                        commentDto.getPublishTime(),
                        true
                );
                commentService.updateComment(commentDtoToSave.getId(), commentDtoToSave);


                EditMessageReplyMarkup changedMessage = EditMessageReplyMarkup
                        .builder()
                        .chatId(appProps.getChatId())
                        .messageId(update.getCallbackQuery().getMessage().getMessageId())
                        .replyMarkup(null)
                        .build();

                try {

                    execute(changedMessage);

                } catch (TelegramApiException e) {
                    new RuntimeException(e);
                }


            } else if (data.startsWith("delete_")) {
                var id = data.replace("delete_", "");
                commentService.deleteById(Long.valueOf(id));
                EditMessageReplyMarkup changedMessage = EditMessageReplyMarkup
                        .builder()
                        .chatId(appProps.getChatId())
                        .messageId(update.getCallbackQuery().getMessage().getMessageId())
                        .replyMarkup(null)
                        .build();

                try {

                    execute(changedMessage);

                } catch (TelegramApiException e) {
                    new RuntimeException(e);
                }
            }
        }
    }

    public void sendComment(TelegramMessage message) {
       /*var url = InlineKeyboardButton.builder()
                .text("Poem reference")
                .url(message.getReference())
                .build();*/
        var accept = InlineKeyboardButton.builder().text("Accept").callbackData("accept_" + message.getId()).build();
        var delete = InlineKeyboardButton.builder().text("Delete").callbackData("delete_" + message.getId()).build();

        var keyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(accept, delete))
                //.keyboardRow(List.of(url))
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
