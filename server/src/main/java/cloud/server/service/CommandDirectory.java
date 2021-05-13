package cloud.server.service;

import io.netty.channel.ChannelHandlerContext;

public interface CommandDirectory {

    //запускает нужную команду по названию
    <T> T commandActive(Object command);

    String getServerDir();

    ChannelHandlerContext getCtx();

    void setCtx(ChannelHandlerContext ctx);

    void setCurrentServerPath(String currentServerPath);
}
