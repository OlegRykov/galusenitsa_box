package cloud.client.service.impl;

import cloud.client.service.ServerDirectoryWorkService;
import javafx.scene.control.TextArea;

public class ServerDirectory implements ServerDirectoryWorkService {
    @Override
    public void currentDir(TextArea clientDir, String msg) {
        clientDir.clear();
        clientDir.appendText(msg);
    }
}
