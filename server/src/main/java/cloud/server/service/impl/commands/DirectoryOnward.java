package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandWork;


import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryOnward implements CommandWork {

    private String name;
    private CommandDirectory commandDirectory;

    public DirectoryOnward(CommandDirectory commandDirectory) {
        this.commandDirectory = commandDirectory;
        name = Command.ONWARD_SERVER;
    }

    @Override
    public String commandWork(Object msg) {
        Path path = Paths.get(commandDirectory.getServerDir() + "\\" + msg);
        if (path.toFile().isDirectory()){
            commandDirectory.setCurrentServerPath(commandDirectory.getServerDir() + "\\"  + msg);
        }
        return commandDirectory.getServerDir();
    }

    public String getName() {
        return name;
    }
}
