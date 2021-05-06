package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandWork;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFile implements CommandWork {
    private String name;
    private CommandDirectory commandDirectory;

    public DeleteFile(CommandDirectory commandDirectory) {
        this.commandDirectory = commandDirectory;
        name = Command.DELETE_FILE;
    }

    @Override
    public String commandWork(Object msg) {
        Path path = Paths.get(commandDirectory.getServerDir() + "\\" + msg);
        boolean result = false;
        try {
            result = Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (result) {
            return "Файл успешно удален";
        }
        return "Не удалось удалить данный файл.";
    }

    @Override
    public String getName() {
        return name;
    }
}
