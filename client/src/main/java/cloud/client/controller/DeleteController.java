package cloud.client.controller;

import cloud.client.service.AppendText;
import cloud.client.service.FilesWork;
import cloud.client.service.NetworkService;
import cloud.commands.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class DeleteController {
    private Stage deleteStage;
    private MainController mainController;

    private boolean isVisible;

    private CyclicBarrier cyclicBarrier;
    private NetworkService networkService;
    private ListView serverFiles;
    private AppendText appendText;

    @FXML
    public TextArea textArea;
    @FXML
    public HBox buttonChoice;
    @FXML
    public HBox buttonClose;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void yesChoice(ActionEvent actionEvent) {
        cyclicBarrier = mainController.getCyclicBarrier();
        networkService = mainController.getNetworkService();
        serverFiles = mainController.getServerFiles();
        appendText = mainController.getAppendText();


        networkService.sendCommand(Command.DELETE_FILE + System.lineSeparator() +
                serverFiles.getSelectionModel().getSelectedItem().toString());
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

    public void noChoice(ActionEvent actionEvent) {
        deleteStage = mainController.getDeleteStage();
        deleteStage.close();

        buttonChoice.setVisible(!isVisible);
        buttonChoice.setManaged(!isVisible);
        buttonClose.setVisible(isVisible);
        buttonClose.setManaged(isVisible);
    }
}
