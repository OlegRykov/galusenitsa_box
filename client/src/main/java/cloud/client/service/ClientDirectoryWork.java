package cloud.client.service;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public interface ClientDirectoryWork {
    //возвращает текущую директорию
    String currentDir(TextArea clientDir);

    //хождение по директории назад
    String backDir(TextArea clientDir);

    //хождение по директории вперед
    String onwardDir(TextArea clientDir, ListView clientFiles);

    //обновляет текущую директорию клиента
    void appendDir(TextArea clientDir, String url);
}
