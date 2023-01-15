package ru.otus.homework16.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework16.model.Comment;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentListener {

    private static LocalDateTime lastAddCommentInstant = LocalDateTime.now();

    @PrePersist
    public synchronized void onPrePersist(final Comment toSave) {
        lastAddCommentInstant = LocalDateTime.now();
    }

    public LocalDateTime getLastAddCommentInstant() {
        return lastAddCommentInstant;
    }
}
