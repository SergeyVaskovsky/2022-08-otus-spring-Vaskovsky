package ru.otus.homework17.actuator;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LastAddCommentInstantImpl implements LastAddCommentInstant{
    private LocalDateTime lastAddCommentInstant = LocalDateTime.now();

    @Override
    public LocalDateTime getLastAddCommentInstant() {
        return lastAddCommentInstant;
    }

    @Override
    public synchronized void setLastAddCommentInstant(LocalDateTime instant) {
        lastAddCommentInstant = instant;
    }
}
