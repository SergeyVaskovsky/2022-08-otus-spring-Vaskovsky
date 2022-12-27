package ru.otus.homework15.shell;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework15.gateway.Nature;
import ru.otus.homework15.model.Butterfly;
import ru.otus.homework15.model.Caterpillar;

@ShellComponent
@RequiredArgsConstructor
public class NatureShell {

    private final Nature nature;
    private static final String[] BUTTERFLY_NAMES = {
            "Голубянки", "Пестрянки", "Волнянки",
            "Адмирал", "Павлиний глаз", "Бархатница",
            "Толстоголовка", "Махаон", "Морфо Пелеида",
            "Кавалеры", "Крапивница", "Монарх",
            "Мертвая голова", "Перламутровка", "Шашечница",
            "Желтушка луговая", "Сенница обыкновенная", "Чернушка",
    };

    @ShellMethod(value = "start turning into", key = "start")
    public void start() {
        Caterpillar caterpillar = generateCaterpillar();
        System.out.println(caterpillar.getName());
        Butterfly butterfly = nature.process(caterpillar);
        System.out.println(butterfly.getName());
    }

    private Caterpillar generateCaterpillar() {
        return new Caterpillar( BUTTERFLY_NAMES[ RandomUtils.nextInt( 0, BUTTERFLY_NAMES.length ) ] );
    }
}
