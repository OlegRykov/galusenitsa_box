package cloud.client.service.impl;

import cloud.client.service.ServerDirectoryWork;
import javafx.scene.control.TextArea;

public class ServerDirectory implements ServerDirectoryWork {
    @Override
    public void currentDir(TextArea clientDir, String msg) {
        clientDir.clear();
        clientDir.appendText(msg);
    }
}
