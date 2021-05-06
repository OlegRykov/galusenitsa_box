package cloud.server.service;

public interface CommandWork {

    //запускает реализацию команды
    public String commandWork(Object msg);

    //возвращает название команды
    public String getName();
}
