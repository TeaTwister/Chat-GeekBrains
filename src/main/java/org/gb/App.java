package org.gb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.security.auth.login.LoginException;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static double deltaX;
    private static double deltaY;

    private static Scene scene;
    private static ChatClient communicator;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("loginTemplate");
        scene = new Scene(root, 600, 600);
        stage.initStyle(StageStyle.UNDECORATED);
        setDragHandler(stage);
        stage.setMinHeight(600);
        stage.setMinWidth(550);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        Stage stage = (Stage) scene.getWindow();
        setDragHandler(stage);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void setDragHandler(Stage s) {
        scene.setOnMousePressed(mouseEvent -> {
            deltaX = mouseEvent.getSceneX();
            deltaY = mouseEvent.getSceneY();
        });
        scene.setOnMouseDragged(mouseEvent -> {
            s.setX(mouseEvent.getScreenX() - deltaX);
            s.setY(mouseEvent.getScreenY() - deltaY);
        });
    }

    public static ChatClient getCommunicator() {
        return communicator;
    }

    public static void login(String name) throws IOException, LoginException {
        communicator = new ChatClient(name);
    }

    public static void maximize() {
        Stage stage = (Stage) scene.getWindow();
        stage.setMaximized(!stage.isMaximized());
    }

    public static void minimize() {
        Stage stage = (Stage) scene.getWindow();
        stage.setIconified(!stage.isIconified());
    }
}