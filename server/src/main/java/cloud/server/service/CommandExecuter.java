package cloud.server.service;

public interface CommandExecuter {

    //запускает реализацию команды
    String commandExecute(Object msg);

    //возвращает название команды
    String getCommandName();
}
