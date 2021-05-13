package cloud.client.service;

import javafx.scene.control.TextArea;

public interface ServerDirectoryWorkService {
    //возвращает текущую директорию сервера
    void currentDir(TextArea clientDir, String msg);
}
