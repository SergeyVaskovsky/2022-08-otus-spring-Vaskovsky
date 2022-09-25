package ru.otus.command;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.service.TestService;

@ShellComponent
public class Commands {

    private static final int QUIT_CODE = 0;
    private final TestService testService;
    private String userName;

    @Autowired
    public Commands(TestService testService) {
        this.testService = testService;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
        this.userName = userName;
        return String.format("Welcome: %s", userName);
    }

    @ShellMethod(value = "Test", key = {"t", "test"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public void test() {
        testService.test();
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null ? Availability.unavailable("Please login before") : Availability.available();
    }

    @ShellMethod(value = "Quit", key = {"q", "quit"})
    public void quit() {
        System.exit(QUIT_CODE);
    }
}
