package cloud.client.controller;

import cloud.client.factory.Factory;
import cloud.client.service.*;
import cloud.commands.Command;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class MainController implements Initializable {

    private String result = "";

    private FilesWork clientFilesList;
    private FilesWork serverFilesList;

    private AppendText appendText;

    private Stage mainStage;

    private Stage deleteStage;
    private DeleteController delCon;

    private Stage createFileStage;
    private CreateFileController fileController;

    private ClientDirectoryWork clientDir;
    private ServerDirectoryWork serverDir;

    private CyclicBarrier cyclicBarrier;

    private NetworkService networkService;

    final String START_CLIENT_PATH = "/Users";

    @FXML
    public ListView clientFiles;
    @FXML
    public ListView serverFiles;
    @FXML
    public TextArea clientDirectory;
    @FXML
    public TextArea serverDirectory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            mainStage = (Stage) clientDirectory.getScene().getWindow();

            cyclicBarrier = new CyclicBarrier(2);

            appendText = Factory.appendText();

            networkService = Factory.getNetworkService();

            serverDir = Factory.serverDirectoryWork();

            serverFilesList = Factory.serverFiles();

            clientDir = Factory.clientDirectory();
            clientDir.appendDir(clientDirectory, START_CLIENT_PATH);

            clientFilesList = Factory.clientFiles();
            clientFilesList.filesInCurDir(clientFiles, START_CLIENT_PATH);

            createCommandResultHandler();

            refresh();

            mainStage.setOnCloseRequest(windowEvent -> {
                exit();
            });
        });

    }

    private void createCommandResultHandler() {
        new Thread(() -> {
            byte[] buffer = new byte[1024];
            while (true) {
                int countBytes = networkService.commandResult(buffer);
                result = new String(buffer, 0, countBytes);
                System.out.println(result);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @FXML
    public void refresh() {
        Platform.runLater(() -> {
            clientDir.appendDir(clientDirectory, clientDir.currentDir(clientDirectory));
            clientFilesList.filesInCurDir(clientFiles, clientDir.currentDir(clientDirectory));
            try {
                networkService.sendCommand(Command.SERVER_DIR);
                cyclicBarrier.await();
                serverDir.currentDir(serverDirectory, result);
                networkService.sendCommand(Command.REFRESH);
                cyclicBarrier.await();
                serverFilesList.filesInCurDir(serverFiles, result);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        System.out.println(Command.REFRESH);
    }


    @FXML
    public void sendFile(ActionEvent actionEvent) {

        System.out.println(Command.SEND_FILE);
    }

    @FXML
    public void saveFile(ActionEvent actionEvent) {
        System.out.println(Command.SAVE_FILE);
    }

    @FXML
    public void createDir(ActionEvent actionEvent) {
        if (createFileStage == null) {
            createFileWin();
        }
        appendText.appendTextarea(fileController.textArea,
                "Введите имя файла.");
        createFileStage.show();
        System.out.println(Command.CREATE_DIR);
    }

    @FXML
    public void deleteFile(ActionEvent actionEvent) {
        if (serverFiles.getSelectionModel().getSelectedItem() != null){
            String item = serverFiles
                    .getSelectionModel().getSelectedItem().toString();
            if (deleteStage == null) {
                createDeleteWin();
            }
            appendText.appendTextarea(delCon.textArea,
                    "Вы действительно хотите удалить этот файл с именем: " + item + " ?");
            deleteStage.show();
        }
    }

    @FXML
    public void exit() {
        networkService.closeConnection();
        System.out.println(Command.EXIT);
        System.exit(0);
    }

    @FXML
    public void backClient(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            clientDir.appendDir(clientDirectory, clientDir.backDir(clientDirectory));
            clientFilesList.filesInCurDir(clientFiles, clientDir.currentDir(clientDirectory));
        });

    }

    @FXML
    public void onwardClient(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            clientDir.appendDir(clientDirectory, clientDir.onwardDir(clientDirectory, clientFiles));
            clientFilesList.filesInCurDir(clientFiles, clientDir.currentDir(clientDirectory));
        });

    }

    @FXML
    public void backServer(ActionEvent actionEvent) {
        networkService.sendCommand(Command.BACK_SERVER);
        try {
            cyclicBarrier.await();
            refresh();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println(Command.BACK_SERVER);
    }

    @FXML
    public void onwardServer(ActionEvent actionEvent) {
        networkService.sendCommand(Command.ONWARD_SERVER  + System.lineSeparator() + serverFiles
                .getSelectionModel().getSelectedItem());

        try {
            cyclicBarrier.await();
            refresh();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Command.ONWARD_SERVER);
    }

    public void createDeleteWin(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/deleteWindow.fxml"));
            Parent root = fxmlLoader.load();
            deleteStage = new Stage();
            deleteStage.setScene(new Scene(root, 250, 200));
            delCon = fxmlLoader.getController();
            delCon.setMainController(this);
            deleteStage.initModality(Modality.APPLICATION_MODAL);
            deleteStage.initStyle(StageStyle.UTILITY);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getDeleteStage() {
        return deleteStage;
    }

    public void createFileWin(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/createFileWindow.fxml"));
            Parent root = fxmlLoader.load();
            createFileStage = new Stage();
            createFileStage.setScene(new Scene(root, 250, 200));
            fileController = fxmlLoader.getController();
            fileController.setMainController(this);
            createFileStage.initModality(Modality.APPLICATION_MODAL);
            createFileStage.initStyle(StageStyle.UTILITY);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getCreateFileStage() {
        return createFileStage;
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public ListView getServerFiles() {
        return serverFiles;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }

    public String getResult() {
        return result;
    }

    public AppendText getAppendText() {
        return appendText;
    }
}
