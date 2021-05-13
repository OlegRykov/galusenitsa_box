package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandExecuter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RefreshCommand implements CommandExecuter {
    private CommandDirectory commandDirectory;
    private Command command;

    public RefreshCommand(CommandDirectory commandDirectory) {
        command = Command.REFRESH;
        this.commandDirectory = commandDirectory;
    }

    @Override
    public String commandExecute(Object msg) {
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

        if (sb.toString().isEmpty()) {
            sb.append(Command.IS_EMPTY.name());
        }
        return sb.toString();
    }

    @Override
    public String getCommandName() {
        return command.name();
    }
}
