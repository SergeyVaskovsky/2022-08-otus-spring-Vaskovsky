package ru.otus.homework14.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework14.config.JobConfig;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {
    private final JobOperator jobOperator;
    private Long executionId = null;

    //http://localhost:8080/h2-console/

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "start")
    public void startMigrationJobWithJobOperator() throws Exception {
        executionId = jobOperator.start(JobConfig.IMPORT_JOB_NAME, "");
        System.out.println(jobOperator.getSummary(executionId));
    }
}
