package cloud.server.service;

public interface CommandDirectory {

    //запускает нужную команду по названию
    public String commandActive(String command);

    public String getServerDir();

    public void setCurrentServerPath(String currentServerPath);
}
