package ru.otus.homework17.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class ZeroCommentsIndicator implements HealthIndicator {
    private final static long IDLE_PERIOD_IN_SECONDS = 10;
    private final static long DELAY = 1000;
    private long periodWithoutAddComment;
    private final LastAddCommentInstant lastAddCommentInstant;

    @Override
    public Health health() {
        if (periodWithoutAddComment > IDLE_PERIOD_IN_SECONDS) {
            return Health
                    .down()
                    .status(Status.DOWN)
                    .withDetail("message", String.format("Никто не оставлял комментарии больше %d секунд!", periodWithoutAddComment))
                    .build();
        } else {
            return Health.up().build();
        }
    }

    @Scheduled(fixedDelay = DELAY)
    public void checkTimeOfNewComments() {

        periodWithoutAddComment = ChronoUnit.SECONDS.between(
                lastAddCommentInstant.getLastAddCommentInstant(),
                LocalDateTime.now());

    }
}