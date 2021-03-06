package cloud.client.service.impl;

import cloud.client.service.FilesWorkService;
import cloud.commands.Command;
import javafx.scene.control.ListView;

public class ServerFileList implements FilesWorkService {
    @Override
    public void filesInCurDir(ListView files, String url) {
        files.getItems().clear();

        if (url.equals(Command.IS_EMPTY.name())) {
            return;
        }
        String[] list = url.split("\n");
        for (int i = 0; i < list.length; i++) {
            files.getItems().add(list[i]);
        }
    }
}
