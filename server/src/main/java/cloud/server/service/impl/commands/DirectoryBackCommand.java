package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandExecuter;

import java.nio.file.Paths;

public class DirectoryBackCommand implements CommandExecuter {
    private CommandDirectory commandDirectory;
    private Command command;

    public DirectoryBackCommand(CommandDirectory commandDirectory) {
        command = Command.DIRECTORY_BACK;
        this.commandDirectory = commandDirectory;
    }

    @Override
    public String commandExecute(Object msg) {
        commandDirectory.setCurrentServerPath(String.valueOf(Paths.get(commandDirectory.getServerDir() + "/..")
                .normalize()));
        return commandDirectory.getServerDir();
    }

    @Override
    public String getCommandName() {
        return command.name();
    }
}
