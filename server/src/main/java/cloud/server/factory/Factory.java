package cloud.server.factory;

import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandExecutor;
import cloud.server.service.ServerService;
import cloud.server.service.impl.NettyServerService;
import cloud.server.service.impl.ServerCommandDir;
import cloud.server.service.impl.commands.*;

import java.util.Arrays;
import java.util.List;

public class Factory {

    public static ServerService getServerService() {
        return NettyServerService.getNettyServer();
    }

    public static CommandDirectory getCommandDirectory() {
        return new ServerCommandDir();
    }

    public static List<CommandExecutor> commandWorkList(CommandDirectory commandDirectory) {
        return Arrays.asList(new DirectoryBackCommand(commandDirectory),
                new DirectoryOnwardCommand(commandDirectory), new RefreshCommand(commandDirectory),
                new ServerDirectoryCommand(commandDirectory), new DeleteFileCommand(commandDirectory),
                new CreateFileCommand(commandDirectory), new SaveFileCommand(commandDirectory),
                new SendFileCommand(commandDirectory));
    }
}
