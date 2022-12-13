package ru.otus.homework11.changelogs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final DbInitializer dbInitializer;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        dbInitializer.initDb();
    }
}
