package cloud.client.controller;

import cloud.client.service.AppendText;
import cloud.client.service.NetworkService;

import cloud.commands.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
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
    private AppendText appendText;

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
    }

    public void isOk(ActionEvent actionEvent) {
        cyclicBarrier = mainController.getCyclicBarrier();
        networkService = mainController.getNetworkService();
        appendText = mainController.getAppendText();

        networkService.sendCommand(Command.CREATE_DIR + System.lineSeparator() +
                textField.getText());
        try {
            cyclicBarrier.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        appendText.appendTextarea(textArea, mainController.getResult());

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
