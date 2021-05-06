package cloud.client.service.impl;

import cloud.client.service.NetworkService;
import cloud.commands.CommandConst;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.*;
import java.net.Socket;

public class IONetworkService implements NetworkService {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    private static IONetworkService clientService;

    public static ObjectDecoderInputStream in;
    public static ObjectEncoderOutputStream out;

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
            out = new ObjectEncoderOutputStream(socket.getOutputStream());
            in = new ObjectDecoderInputStream(socket.getInputStream(), CommandConst.MAX_FILE_SIZE);
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
    public void sendCommand(Object command) {
        try {
            out.writeObject(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendFile(File file) {
        try (InputStream inFile = new BufferedInputStream(new FileInputStream(file))) {
            out.writeObject(inFile.readAllBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object commandResult() {
        try {
            return in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return "";
        }
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

    @Override
    public void saveFile(File file, Object uploadFile) {
        if (!file.exists()){
            try (OutputStream os = new BufferedOutputStream(new FileOutputStream(file, true))) {
                os.write((byte[]) uploadFile);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
