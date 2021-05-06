package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandExecuter;

public class ServerDirectoryCommand implements CommandExecuter {
    private CommandDirectory commandDirectory;
    private Command command;

    public ServerDirectoryCommand(CommandDirectory commandDirectory) {
        command = Command.SERVER_DIRECTORY;
        this.commandDirectory = commandDirectory;
    }

    @Override
    public String commandExecute(Object msg) {
        return commandDirectory.getServerDir();
    }

    @Override
    public String getCommandName() {
        return command.name();
    }
}
