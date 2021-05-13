package cloud.client.controller;

import cloud.client.factory.Factory;
import cloud.client.service.CommandResultService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class InfoWindowController {
    private MainController mainController;
    private CommandResultService commandResultService;

    @FXML
    public TextArea textArea;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        commandResultService = Factory.getCommandResultService();
    }
}
