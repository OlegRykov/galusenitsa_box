package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandExecutor;
import cloud.server.service.impl.handler.FilesWriteHandler;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;

public class SendFileCommand implements CommandExecutor {
    private CommandDirectory commandDirectory;
    private ChannelHandlerContext ctx;
    private Command command;

    public SendFileCommand(CommandDirectory commandDirectory) {
        command = Command.SEND_FILE;
        this.commandDirectory = commandDirectory;
    }

    @Override
    public String commandExecute(Object msg) {
        ctx = commandDirectory.getCtx();
        System.out.println(msg);

        File file = new File(commandDirectory.getServerDir() +
                "\\" + msg);

        if (!file.exists()) {
            ctx.pipeline()
                    .addLast("fileHandler", new FilesWriteHandler(file));
            return Command.READY_TO_SEND.name();
        }

        return Command.WRONG_FILE.name();
    }

    @Override
    public String getCommandName() {
        return command.name();
    }
}
