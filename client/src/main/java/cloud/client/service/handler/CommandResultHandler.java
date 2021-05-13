package cloud.client.service.handler;

import cloud.client.service.CommandResultService;
import cloud.client.service.NetworkService;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CommandResultHandler implements CommandResultService {

    private Object result;
    private static CommandResultHandler commandResultHandler;

    public CommandResultHandler() {
    }

    public static CommandResultHandler getCommandResultHandler() {
        if (commandResultHandler == null) {
            commandResultHandler = new CommandResultHandler();
        }
        return commandResultHandler;
    }

    @Override
    public void createCommandResultHandler(CyclicBarrier cb, NetworkService networkService) {
        new Thread(() -> {
            while (true) {
                result = networkService.commandResult();
                try {
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public Object getResult() {
        return result;
    }
}
