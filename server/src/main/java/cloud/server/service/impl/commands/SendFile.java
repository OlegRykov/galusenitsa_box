package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandWork;


public class SendFile implements CommandWork {
    private String name;
    private CommandDirectory commandDirectory;

    public SendFile(CommandDirectory commandDirectory) {
        this.commandDirectory = commandDirectory;
        name = Command.SEND_FILE;
    }

    @Override
    public String commandWork(Object msg) {


        return "Успешно";
    }

    @Override
    public String getName() {
        return name;
    }
}
