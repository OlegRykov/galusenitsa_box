package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandWork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Refresh implements CommandWork {
    private String name;
    private CommandDirectory commandDirectory;

    public Refresh(CommandDirectory commandDirectory) {
        this.commandDirectory = commandDirectory;
        name = Command.REFRESH;
    }

    @Override
    public String commandWork(Object msg) {
        StringBuilder sb = new StringBuilder();
        try {
            Files.list(Paths.get(commandDirectory.getServerDir()))
                    .filter(path -> path.toFile().isDirectory() || path.toFile().isFile()).forEach((e) -> {
                if (!e.toFile().isHidden()) {
                    sb.append(e.getFileName() + "\n");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (sb.toString().isEmpty()){
            sb.append(Command.IS_EMPTY);
        }

        return sb.toString();
    }

    @Override
    public String getName() {
        return name;
    }
}
