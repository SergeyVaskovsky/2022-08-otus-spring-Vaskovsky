package ru.otus.homework17.actuator;

import java.time.LocalDateTime;

public interface LastAddCommentInstant {
    LocalDateTime getLastAddCommentInstant();
    void setLastAddCommentInstant(LocalDateTime instant);
}
