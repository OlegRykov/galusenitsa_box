package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandExecutor;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFileCommand implements CommandExecutor {
    private CommandDirectory commandDirectory;
    private Command command;

    public DeleteFileCommand(CommandDirectory commandDirectory) {
        command = Command.DELETE_FILE;
        this.commandDirectory = commandDirectory;
    }

    @Override
    public String commandExecute(Object msg) {
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
    public String getCommandName() {
        return command.name();
    }
}
