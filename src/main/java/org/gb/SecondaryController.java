package org.gb;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SecondaryController {

    public JFXTextArea clientText;
    public VBox chatBox;
    public StackPane mainParent;
    public ScrollPane scroll;

    @FXML
    private void initialize() {
        App.getCommunicator().setController(this);
        App.getCommunicator().receiveTo();
        chatBox.heightProperty().addListener((ignored1, ignored2, ignored3) -> scroll.setVvalue(1.));
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("loginTemplate");
    }

    public void send() throws IOException {
        String text = clientText.getText().trim();
        if (text.length() != 0) {
            App.getCommunicator().sendMessage(Message.make().withText(text));
        }
        clientText.clear();
    }

    public void receive(Message m, boolean flag) {
        chatBox.getChildren().add(new Bubble(m, flag));
    }

    public void maximize() {
        App.maximize();
    }

    public void exit() {
        Platform.exit();
    }

    public void minimize() {
        App.minimize();
    }
}