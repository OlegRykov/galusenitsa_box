package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandExecutor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFileCommand implements CommandExecutor {
    private CommandDirectory commandDirectory;
    private Command command;

    public CreateFileCommand(CommandDirectory commandDirectory) {
        command = Command.CREATE_FILE;
        this.commandDirectory = commandDirectory;
    }

    @Override
    public String commandExecute(Object msg) {
        Path path = Paths.get(commandDirectory.getServerDir() + "\\" + msg);
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            return "Директория с таким именем уже присутствует.";
        }
        return "Директория была успешно создана.";
    }

    @Override
    public String getCommandName() {
        return command.name();
    }
}
