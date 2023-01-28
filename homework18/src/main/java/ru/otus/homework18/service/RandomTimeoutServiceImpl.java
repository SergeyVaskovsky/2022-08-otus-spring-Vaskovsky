package ru.otus.homework18.service;

import org.springframework.stereotype.Service;
import ru.otus.homework18.exception.CrashSleepException;

import java.util.Random;

@Service
public class RandomTimeoutServiceImpl implements RandomTimeoutService {
    @Override
    public void sleepRandomTimeout() {
        Random rand = new Random();
        int randomNum = rand.nextInt(5) + 1;
        try {
            Thread.sleep(randomNum * 1000);
        } catch (InterruptedException e) {
            throw new CrashSleepException();
        }
    }
}

