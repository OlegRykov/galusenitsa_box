package cloud.client.service.impl;

import cloud.client.service.FilesWork;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientFilesList implements FilesWork {
    @Override
    public void filesInCurDir(ListView files, String url) {
        try {
            files.getItems().clear();
            Files.list(Paths.get(url))
                    .filter(path -> path.toFile().isDirectory() || path.toFile().isFile()).forEach((e) -> {
                if (!e.toFile().isHidden()) {
                    files.getItems().add(e.getFileName());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
