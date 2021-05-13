package cloud.client.service.impl;

import cloud.client.service.TextAppenderService;
import javafx.scene.control.TextArea;

public class AppendInfo implements TextAppenderService
{
    @Override
    public void appendTextarea(TextArea textArea, String text) {
        textArea.clear();
        textArea.appendText(text);
    }
}
