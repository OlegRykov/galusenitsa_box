package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandWork;

public class SaveFile implements CommandWork {
    private String name;

    public SaveFile(CommandDirectory commandDirectory) {
        name = Command.SAVE_FILE;
    }

    @Override
    public String commandWork(Object msg) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
