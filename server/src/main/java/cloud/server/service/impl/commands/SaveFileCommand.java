package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandExecuter;
import io.netty.channel.ChannelHandlerContext;

import java.io.*;

public class SaveFileCommand implements CommandExecuter {
    private Command command;
    private CommandDirectory commandDirectory;
    private ChannelHandlerContext ctx;

    public SaveFileCommand(CommandDirectory commandDirectory) {
        command = Command.SAVE_FILE;
        this.commandDirectory = commandDirectory;
    }

    @Override
    public String commandExecute(Object msg) {
        ctx = commandDirectory.getCtx();

        File file = new File(commandDirectory.getServerDir() +
                "\\" + msg);
        if (file.isFile()) {
            try (InputStream inFile = new BufferedInputStream(new FileInputStream(file))) {
                ctx.writeAndFlush(inFile.readAllBytes());
                return Command.READY_TO_SAVE.name();
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
