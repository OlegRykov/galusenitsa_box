package cloud.client.factory;

import cloud.client.service.*;
import cloud.client.service.handler.CommandResultHandler;
import cloud.client.service.impl.*;

public class Factory {

    public static FilesWorkService clientFiles() {
        return new ClientFilesList();
    }

    public static FilesWorkService serverFiles() {
        return new ServerFileList();
    }

    public static ClientDirectoryWorkService clientDirectory() {
        return new ClientDirectory();
    }

    public static ServerDirectoryWorkService serverDirectoryWork() {
        return new ServerDirectory();
    }

    public static NetworkService getNetworkService() {
        return IONetworkService.getClientService();
    }

    public static CommandResultService getCommandResultService() {
        return CommandResultHandler.getCommandResultHandler();
    }

    public static TextAppenderService appendText() {
        return new AppendInfo();
    }
}
