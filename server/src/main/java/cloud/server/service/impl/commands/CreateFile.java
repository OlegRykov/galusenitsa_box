package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandWork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFile implements CommandWork {
    private String name;
    private CommandDirectory commandDirectory;

    public CreateFile(CommandDirectory commandDirectory) {
        this.commandDirectory = commandDirectory;
        name = Command.CREATE_DIR;
    }

    @Override
    public String commandWork(Object msg) {
        Path path = Paths.get(commandDirectory.getServerDir() + "\\" + msg);
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            return "Директория с таким именем уже присутствует.";
        }
        return "Директория была успешно создана.";
    }

    @Override
    public String getName() {
        return name;
    }
}
