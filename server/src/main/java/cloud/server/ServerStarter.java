package cloud.server;

import cloud.server.factory.Factory;

public class ServerStarter {
    public static void main(String[] args) {
        Factory.getServerService().startServer();
    }
}
