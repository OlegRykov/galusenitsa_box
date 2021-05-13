package cloud.server.service.impl;

import cloud.commands.CommandConst;
import cloud.server.factory.Factory;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandExecutor;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class ServerCommandDir implements CommandDirectory {
    private String currentServerPath = CommandConst.SERVER_PATH;
    private File file = new File(currentServerPath);
    private ChannelHandlerContext ctx;
    Object result;

    private final List<CommandExecutor> commandWorksList;

    public ServerCommandDir() {
        file.mkdir();
        commandWorksList = Factory.commandWorkList(this);
    }

    @Override
    public Object commandActive(Object command) {
        int countLimit = 2;
        String[] commandAndInfo = command.toString().split(System.lineSeparator(), countLimit);

        for (int i = 0; i < commandWorksList.size(); i++) {
            if (commandWorksList.get(i).getCommandName().equals(commandAndInfo[0])) {
                if (commandAndInfo.length < countLimit) {
                    result = commandWorksList.get(i).commandExecute(commandAndInfo[0]);
                } else {
                    result = commandWorksList.get(i).commandExecute(commandAndInfo[1]);
                }
            }
        }
        return result;
    }

    @Override
    public String getServerDir() {
        return Paths.get(currentServerPath).toString();
    }

    @Override
    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    @Override
    public void setCurrentServerPath(String currentServerPath) {
        this.currentServerPath = currentServerPath;
    }
}
