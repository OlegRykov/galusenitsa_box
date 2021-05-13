package cloud.server.service;

public interface CommandExecutor {

    //запускает реализацию команды
    Object commandExecute(Object msg);

    //возвращает название команды
    String getCommandName();
}
