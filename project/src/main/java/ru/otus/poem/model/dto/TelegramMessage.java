package ru.otus.poem.model.dto;

import lombok.Value;

@Value
public class TelegramMessage {
    Long id;
    String reference;
    String text;
}
