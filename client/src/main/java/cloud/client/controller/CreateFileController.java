package cloud.client.controller;

import cloud.client.factory.Factory;
import cloud.client.service.CommandResultService;
import cloud.client.service.TextAppenderService;
import cloud.client.service.NetworkService;

import cloud.commands.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CreateFileController {
    private Stage createFileStage;
    private MainController mainController;

    private boolean isVisible;

    private CyclicBarrier cyclicBarrier;
    private NetworkService networkService;
    private TextAppenderService appendText;
    private CommandResultService commandResultService;

    @FXML
    public TextArea textArea;
    @FXML
    public HBox buttonChoice;
    @FXML
    public HBox buttonClose;
    @FXML
    public TextField textField;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        commandResultService = Factory.getCommandResultService();
    }

    public void isOk(ActionEvent actionEvent) {
        cyclicBarrier = mainController.getCyclicBarrier();
        networkService = Factory.getNetworkService();
        appendText = mainController.getAppendText();

        networkService.sendCommand(Command.CREATE_FILE.name() + System.lineSeparator() +
                textField.getText());
        try {
            cyclicBarrier.await();

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        appendText.appendTextarea(textArea, commandResultService.getResult().toString());

        buttonChoice.setVisible(isVisible);
        buttonChoice.setManaged(isVisible);
        buttonClose.setVisible(!isVisible);
        buttonClose.setManaged(!isVisible);

        mainController.refresh();
    }

    public void cancel(ActionEvent actionEvent) {
        createFileStage = mainController.getCreateFileStage();
        createFileStage.close();
        textField.clear();
        textArea.clear();

        buttonChoice.setVisible(!isVisible);
        buttonChoice.setManaged(!isVisible);
        buttonClose.setVisible(isVisible);
        buttonClose.setManaged(isVisible);
    }
}
