package cloud.client.service;

public interface NetworkService {
    //отправка комманд, сообщений на сервер
    void sendCommand(String command);

    //результат выполненных команд
    int commandResult(byte[] buffer);

    //закрытие сокета и стримов
    void closeConnection();

}
