package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandExecuter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryOnwardCommand implements CommandExecuter {

    private CommandDirectory commandDirectory;
    private Command command;

    public DirectoryOnwardCommand(CommandDirectory commandDirectory) {
        command = Command.DIRECTORY_ONWARD;
        this.commandDirectory = commandDirectory;
    }

    @Override
    public String commandExecute(Object msg) {
        Path path = Paths.get(commandDirectory.getServerDir() + "\\" + msg);
        if (path.toFile().isDirectory()){
            commandDirectory.setCurrentServerPath(commandDirectory.getServerDir() + "\\"  + msg);
        }
        return commandDirectory.getServerDir();
    }

    @Override
    public String getCommandName() {
        return command.name();
    }
}
