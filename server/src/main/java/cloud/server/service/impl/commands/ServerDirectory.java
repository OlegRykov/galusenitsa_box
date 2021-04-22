package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandWork;

public class ServerDirectory implements CommandWork {
    private String name;
    CommandDirectory commandDirectory;

    public ServerDirectory(CommandDirectory commandDirectory) {
        this.commandDirectory = commandDirectory;
        name = Command.SERVER_DIR;
    }

    @Override
    public String commandWork(Object msg) {
        return commandDirectory.getServerDir();
    }

    @Override
    public String getName() {
        return name;
    }
}
