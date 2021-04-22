package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandWork;

import java.nio.file.Paths;

public class DirectoryBack implements CommandWork {
    private String name;
    private CommandDirectory commandDirectory;

    public DirectoryBack(CommandDirectory commandDirectory) {
        this.commandDirectory = commandDirectory;
        this.name = Command.BACK_SERVER;
    }

    @Override
    public String commandWork(Object msg) {
        commandDirectory.setCurrentServerPath(String.valueOf(Paths.get(commandDirectory.getServerDir() +  "/..")
                .normalize()));
        return commandDirectory.getServerDir();
    }

    @Override
    public String getName() {
        return name;
    }
}
