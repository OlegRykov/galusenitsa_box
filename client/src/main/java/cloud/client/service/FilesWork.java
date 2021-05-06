package cloud.client.service;

import javafx.scene.control.ListView;

public interface FilesWork {
    //возвращает список файлов в текущей директории
    void filesInCurDir (ListView files, String url);
}
