package ru.otus.homework15.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework15.gateway.CarFactory;
import ru.otus.homework15.model.Car;
import ru.otus.homework15.model.Order;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
public class CarFactoryTest {

    @Autowired
    private CarFactory carFactory;

    @Test
    public void produceCarIsSuccessfully() {
        List<String> options = Arrays.asList(
                "Антиблокировочная система",
                "Гидроусилитель руля",
                "Фирменная сигнализация",
                "HI-FI музыкальная система");
        Order order = new Order(
                "Купе",
                3.0f,
                options
        );
        Car car = carFactory.process(order);
        assertThat(car).isNotNull();
        assertThat(car.getCarBody().getName()).isEqualTo("Купе");
        assertThat(car.getEngine().getVolume()).isEqualTo(3.0f);
        assertThat(car.getOptions().getOptionsNames()).containsExactlyElementsOf(options);
    }

}
