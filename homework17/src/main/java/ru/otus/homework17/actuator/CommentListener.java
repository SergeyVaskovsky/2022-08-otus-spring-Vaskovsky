package ru.otus.homework17.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.homework17.model.Comment;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Component
public class CommentListener {

    @Autowired
    private LastAddCommentInstant lastAddCommentInstant;

    @PrePersist
    public void onPrePersist(final Comment toSave) {
        lastAddCommentInstant.setLastAddCommentInstant(LocalDateTime.now());
    }
}
