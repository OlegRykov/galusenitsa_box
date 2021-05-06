package cloud.server.service.impl;

import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandWork;
import cloud.server.service.impl.commands.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ServerCommandDir implements CommandDirectory {
    private String currentServerPath = "C:\\galusenitsa_box";
    File file = new File(currentServerPath);


    private List<CommandWork> commandWorksList;
    private CommandDirectory commandDirectory;

    public ServerCommandDir() {
        file.mkdir();
        commandDirectory = ServerCommandDir.this;
        commandWorksList = Arrays.asList(new DirectoryBack(commandDirectory),
                new DirectoryOnward(commandDirectory), new Refresh(commandDirectory),
                new ServerDirectory(commandDirectory), new DeleteFile(commandDirectory),
                new CreateFile(commandDirectory), new SaveFile(commandDirectory),
                new SendFile(commandDirectory));
    }

    @Override
    public String commandActive(String command) {
        int countLimit = 2;
        String[] commandAndInfo = command.split(System.lineSeparator(), countLimit);
        String result = null;
        for (int i = 0; i < commandWorksList.size(); i++) {
            if (commandWorksList.get(i).getName().equals(commandAndInfo[0])) {
                if (commandAndInfo.length < countLimit) {
                    result = commandWorksList.get(i).commandWork(commandAndInfo[0]);
                } else {
                    result = commandWorksList.get(i).commandWork(commandAndInfo[1]);
                }
            }
        }
        return result;
    }

    @Override
    public String getServerDir() {
        return currentServerPath;
    }

    @Override
    public void setCurrentServerPath(String currentServerPath) {
        this.currentServerPath = currentServerPath;
    }
}
