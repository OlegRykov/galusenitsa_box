package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandExecutor;

import java.io.*;

public class SaveFileCommand implements CommandExecutor {
    private Command command;
    private CommandDirectory commandDirectory;

    public SaveFileCommand(CommandDirectory commandDirectory) {
        command = Command.SAVE_FILE;
        this.commandDirectory = commandDirectory;
    }

    @Override
    public Object commandExecute(Object msg) {
        File file = new File(commandDirectory.getServerDir() +
                "\\" + msg);
        if (file.isFile()) {
            try (InputStream inFile = new BufferedInputStream(new FileInputStream(file))) {
                return inFile.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Command.WRONG_FILE.name();
    }

    @Override
    public String getCommandName() {
        return command.name();
    }
}
