package cloud.client.service.impl;

import cloud.client.service.NetworkService;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class IONetworkService implements NetworkService {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    private static IONetworkService clientService;

    public static InputStream in;
    public static OutputStream out;
    public static OutputStream outFile;
    public static InputStream inFile;

    public static Socket socket;

    private IONetworkService() {
    }

    public static IONetworkService getClientService() {
        if (clientService == null) {
            clientService = new IONetworkService();

            initializeSocket();
            initializeStreams();
        }
        return clientService;
    }

    private static void initializeStreams() {
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeSocket() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendCommand(String command) {
        try {
            out.write(command.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int commandResult(byte[] buffer) {
        try {
            return in.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
