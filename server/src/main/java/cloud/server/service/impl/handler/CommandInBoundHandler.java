package cloud.server.service.impl.handler;

import cloud.server.service.CommandDirectory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CommandInBoundHandler extends SimpleChannelInboundHandler<String> {
    private CommandDirectory commandDir;

    public CommandInBoundHandler(CommandDirectory commandDir) {
        this.commandDir = commandDir;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelCtx, String command) throws Exception {
        channelCtx.writeAndFlush(commandDir.commandActive(command));

    }
}
