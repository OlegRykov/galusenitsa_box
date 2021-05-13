package cloud.client.service;

import java.io.File;

public interface NetworkService {
    //отправка комманд, сообщений на сервер
    void sendCommand(Object command);

    //результат выполненных команд
    Object commandResult();

    //закрытие сокета и стримов
    void closeConnection();

    void sendFile(File file);

    void saveFile(File file, Object uploadFile);
}
