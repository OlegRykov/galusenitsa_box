package cloud.client.factory;

import cloud.client.service.*;
import cloud.client.service.impl.*;

public class Factory {

    public static FilesWork clientFiles() {
        return new ClientFilesList();
    }

    public static FilesWork serverFiles(){
        return new ServerFileList();
    }

    public static ClientDirectoryWork clientDirectory() {
        return new ClientDirectory();
    }

    public static ServerDirectoryWork serverDirectoryWork() {
        return new ServerDirectory();
    }

    public static NetworkService getNetworkService() {
        return IONetworkService.getClientService();
    }

    public static AppendText appendText(){
        return new AppendInfo();
    }
}
