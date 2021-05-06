package cloud.client.service;

import java.util.concurrent.CyclicBarrier;

public interface CommandResultService {

    void createCommandResultHandler(CyclicBarrier cb, NetworkService networkService);

    Object getResult();
}
