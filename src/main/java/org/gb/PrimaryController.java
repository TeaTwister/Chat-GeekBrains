package org.gb;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class PrimaryController {

    public JFXTextField login;
    public Label errorLabel;

    @FXML
    private void switchToSecondary() throws IOException {
        try {
            App.login(login.getText());
        } catch (IOException e) {
            errorPopup("Could not connect to server");
            return;
        } catch (LoginException e) {
            errorPopup(e.getMessage());
            return;
        }
        App.setRoot("chatTemplate");
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

    private void errorPopup(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
