package org.gb;

import com.jfoenix.controls.JFXTextArea;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class Bubble extends HBox {

    public Bubble(Message m, boolean flag) {
        String authorName = m.getAuthor();
        String timeStamp = m.getTimeStamp()
                .toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        String message = m.getText();

        TextField author = new TextField(authorName);
        author.setEditable(false);
        HBox.setHgrow(author, Priority.ALWAYS);
        author.getStyleClass().addAll("transparentTextArea", "chatText", "senderInfo");

        TextField time = new TextField(timeStamp);
        time.setEditable(false);
        time.setAlignment(Pos.CENTER_RIGHT);
        time.setPrefWidth(80.);
        HBox.setHgrow(time, Priority.NEVER);
        time.getStyleClass().addAll("transparentTextArea", "chatText", "senderInfo");

        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        title.setFillHeight(false);
        title.getStyleClass().add("textAreaBack");
        VBox.setVgrow(title, Priority.NEVER);
        title.getChildren().addAll(author, time);

        JFXTextArea messageArea = new JFXTextArea(message);
        messageArea.setEditable(false);
        messageArea.setPrefColumnCount(0);
        messageArea.setPrefRowCount(0);
        messageArea.setPrefHeight(0.);
        messageArea.setPrefWidth(0.);
        messageArea.getStyleClass().addAll("chatText", "chatBox", "transparentTextArea");
        messageArea.setPadding(new Insets(5., 0., 0., 5.));

        Label messageLabel = new Label(message);
        messageLabel.setVisible(false);
        messageLabel.setWrapText(true);
        messageLabel.getStyleClass().addAll("chatText", "chatBox");
        messageLabel.setPadding(new Insets(5.));

        StackPane messageBox = new StackPane();
        messageBox.getStyleClass().add("chatBox");
        VBox.setVgrow(messageBox, Priority.NEVER);
        messageBox.getChildren().addAll(messageArea, messageLabel);

        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.getChildren().addAll(title, messageBox);

        this.setFillHeight(true);
        if (flag) this.setAlignment(Pos.TOP_RIGHT);
        this.getStylesheets().add(this.getClass().getResource("style.css").toString());
        this.getChildren().add(vbox);
    }
}
