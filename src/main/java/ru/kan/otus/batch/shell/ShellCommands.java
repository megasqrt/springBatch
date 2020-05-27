package ru.kan.otus.batch.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.kan.otus.batch.service.BatchService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final BatchService batchService;

    @ShellMethod(value = "Запуск миграции данных", key = {"start", "s"})
    private String runMigration() {
        batchService.launchJob();
        return "Миграция запущена";
    }
}
