package ru.otus.homework15.shell;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework15.gateway.CarFactory;
import ru.otus.homework15.model.*;
import ru.otus.homework15.service.OutputService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CarFactoryShell {

    private final OutputService outputService;

    private final CarFactory carFactory;
    private final static String[] CAR_BODY_NAMES = {
            "Седан", "Универсал", "Кабриолет", "Хэтчбек"
    };
    private final static float[] ENGINE_VOLUMES = {
            1.5f, 2.0f, 2.5f, 3.0f
    };
    private final static String[] OPTION_NAMES = {
            "Антиблокировочная система", "Гидроусилитель руля", "Фирменная сигнализация",
            "HI-FI музыкальная система"
    };

    @ShellMethod(value = "start turning into", key = "start")
    public void start() {
        Order order = new Order(
                generateCarBody().getName(),
                generateEngine().getVolume(),
                generateOptions().getOptionsNames());

        Car car = carFactory.process(order);
        outputService.outputString(car.toString());
    }

    private CarBody generateCarBody() {
        return new CarBody( CAR_BODY_NAMES[ RandomUtils.nextInt( 0, CAR_BODY_NAMES.length ) ] );
    }

    private Engine generateEngine() {
        return new Engine( ENGINE_VOLUMES[ RandomUtils.nextInt( 0, ENGINE_VOLUMES.length ) ] );
    }

    private Options generateOptions() {
        List<String> availableOptions = Arrays.asList(OPTION_NAMES);
        Collections.shuffle(availableOptions);
        int optionsCount = RandomUtils.nextInt( 0, OPTION_NAMES.length );
        List<String> orderedOptions = new ArrayList<>();
        for (int i = 0; i < optionsCount; i++) {
            orderedOptions.add(availableOptions.get(i));
        }
        return new Options(orderedOptions);
    }
}
