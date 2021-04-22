package cloud.server.factory;

import cloud.server.service.CommandDirectory;
import cloud.server.service.ServerService;
import cloud.server.service.impl.NettyServerService;
import cloud.server.service.impl.ServerCommandDir;

public class Factory {

    public static ServerService getServerService() {
        return NettyServerService.getNettyServer();
    }

    public static CommandDirectory getCommandDirectory(){
        return new ServerCommandDir();
    }
}
