package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.config.LocaleProvider;

import java.util.Locale;

@Component
public class MessageSourceWrapper {

    private final Locale locale;
    private final MessageSource messageSource;

    @Autowired
    public MessageSourceWrapper(LocaleProvider localeProvider, MessageSource messageSource) {
        this.locale = localeProvider.getLocale();
        this.messageSource = messageSource;
    }

    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }
}
