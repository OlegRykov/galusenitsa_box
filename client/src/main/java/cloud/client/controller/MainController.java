package cloud.client.controller;

import cloud.client.factory.Factory;
import cloud.client.service.*;
import cloud.commands.Command;
import cloud.commands.CommandConst;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MainController implements Initializable {

    private FilesWorkService clientFilesList;
    private FilesWorkService serverFilesList;

    private TextAppenderService appendText;

    private Stage mainStage;

    private Stage deleteStage;
    private DeleteController delCon;

    private Stage infoStage;
    private InfoWindowController infoWin;

    private Stage createFileStage;
    private CreateFileController fileController;

    private ClientDirectoryWorkService clientDir;
    private ServerDirectoryWorkService serverDir;

    private CyclicBarrier cyclicBarrier;

    private NetworkService networkService;

    private CommandResultService commandResultService;

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
            clientDir.appendDir(clientDirectory, CommandConst.START_CLIENT_PATH);

            clientFilesList = Factory.clientFiles();

            commandResultService = Factory.getCommandResultService();
            commandResultService.createCommandResultHandler(cyclicBarrier, networkService);

            refresh();

            mainStage.setOnCloseRequest(windowEvent -> {
                exit();
            });
        });
    }

    @FXML
    public void refresh() {
        clientDir.appendDir(clientDirectory, clientDir.currentDir(clientDirectory));
        clientFilesList.filesInCurDir(clientFiles, clientDir.currentDir(clientDirectory));
        try {
            networkService.sendCommand(Command.SERVER_DIRECTORY.name());
            cyclicBarrier.await();
            serverDir.currentDir(serverDirectory, commandResultService.getResult().toString());
            networkService.sendCommand(Command.REFRESH.name());
            cyclicBarrier.await();
            serverFilesList.filesInCurDir(serverFiles, commandResultService.getResult().toString());

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sendFile(ActionEvent actionEvent) {
        String item = String.valueOf(clientFiles.getSelectionModel().getSelectedItem());
        File file = new File(clientDir.currentDir(clientDirectory)
                , item);
        if (infoStage == null) {
            createInfoWindow();
        }
        if (file.isFile()) {
            networkService.sendCommand(Command.SEND_FILE.name() + System.lineSeparator() + item);
            try {
                infoStage.show();
                cyclicBarrier.await();

                if (commandResultService.getResult().toString().equals(Command.READY_TO_SEND.name())) {
                    appendText.appendTextarea(infoWin.textArea, "Идет отправка файла. Пожалуйста подождите.");
                    Platform.runLater(() -> {
                        networkService.sendFile(file);
                        refresh();
                        appendText.appendTextarea(infoWin.textArea, "Отправка файла прошла успешно.");
                    });
                } else {
                    appendText.appendTextarea(infoWin.textArea, "Не удалось отправить файл.");
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void saveFile(ActionEvent actionEvent) {
        String item = String.valueOf(serverFiles.getSelectionModel().getSelectedItem());
        File file = new File(clientDir.currentDir(clientDirectory), item);
        if (infoStage == null) {
            createInfoWindow();
        }
        infoStage.show();
        appendText.appendTextarea(infoWin.textArea, "Идет загрузка файла. Пожалуйста ожидайте.");
        Platform.runLater(() -> {
            networkService.sendCommand(Command.SAVE_FILE.name() + System.lineSeparator() + item);
            try {
                cyclicBarrier.await();
                if (!commandResultService.getResult().toString().equals(Command.WRONG_FILE.name())) {
                    networkService.saveFile(file, commandResultService.getResult());
                    refresh();
                    appendText.appendTextarea(infoWin.textArea, "Файл успешно загружен.");
                } else {
                    appendText.appendTextarea(infoWin.textArea, "Не удалось загрузить файл.");
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void createDir(ActionEvent actionEvent) {
        if (createFileStage == null) {
            createFileWin();
        }
        appendText.appendTextarea(fileController.textArea,
                "Введите имя файла.");
        createFileStage.show();
    }

    @FXML
    public void deleteFile(ActionEvent actionEvent) {
        if (serverFiles.getSelectionModel().getSelectedItem() != null) {
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
        System.exit(0);
    }

    @FXML
    public void backClient(ActionEvent actionEvent) {
        clientDir.appendDir(clientDirectory, clientDir.backDir(clientDirectory));
        clientFilesList.filesInCurDir(clientFiles, clientDir.currentDir(clientDirectory));
    }

    @FXML
    public void onwardClient(ActionEvent actionEvent) {
        clientDir.appendDir(clientDirectory, clientDir.onwardDir(clientDirectory, clientFiles));
        clientFilesList.filesInCurDir(clientFiles, clientDir.currentDir(clientDirectory));
    }

    @FXML
    public void backServer(ActionEvent actionEvent) {
        networkService.sendCommand(Command.DIRECTORY_BACK.name());
        try {
            cyclicBarrier.await();
            refresh();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onwardServer(ActionEvent actionEvent) {
        networkService.sendCommand(Command.DIRECTORY_ONWARD.name() + System.lineSeparator() + serverFiles
                .getSelectionModel().getSelectedItem());
        try {
            cyclicBarrier.await();
            refresh();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void createDeleteWin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                    .getResource("view/deleteWindow.fxml"));
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

    public void createInfoWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                    .getResource("view/infoWindow.fxml"));
            Parent root = fxmlLoader.load();
            infoStage = new Stage();
            infoStage.setScene(new Scene(root, 250, 200));
            infoWin = fxmlLoader.getController();
            infoWin.setMainController(this);
            infoStage.initModality(Modality.APPLICATION_MODAL);
            infoStage.initStyle(StageStyle.UTILITY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getInfoStage() {
        return infoStage;
    }

    public void createFileWin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                    .getResource("view/createFileWindow.fxml"));
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

    public TextAppenderService getAppendText() {
        return appendText;
    }
}
