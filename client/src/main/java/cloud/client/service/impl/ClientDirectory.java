package cloud.client.service.impl;

import cloud.client.service.ClientDirectoryWork;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientDirectory implements ClientDirectoryWork {

    @Override
    public String currentDir(TextArea clientDir) {
        return String.valueOf(Paths.get(clientDir.getText()));
    }

    @Override
    public String backDir(TextArea clientDir) {
        return String.valueOf(Paths.get(clientDir.getText() + "/..").normalize());
    }

    @Override
    public String onwardDir(TextArea clientDir, ListView clientFiles) {
        Path path = Paths.get(clientDir.getText() + "/" + clientFiles.getSelectionModel().
                getSelectedItem());
        if (path.toFile().isDirectory()){
            return path.toString();
        }

        return clientDir.getText();
    }

    @Override
    public void appendDir(TextArea clientDir, String url) {
        clientDir.clear();
        Path path = Paths.get(url);
        clientDir.appendText(String.valueOf(path.toAbsolutePath()));
    }
}
