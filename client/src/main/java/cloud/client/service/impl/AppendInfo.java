package cloud.client.service.impl;

import cloud.client.service.AppendText;
import javafx.scene.control.TextArea;

public class AppendInfo implements AppendText
{
    @Override
    public void appendTextarea(TextArea textArea, String text) {
        textArea.clear();
        textArea.appendText(text);
    }
}
