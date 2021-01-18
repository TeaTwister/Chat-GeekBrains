package org.gb;

import javafx.application.Platform;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient {

    private final ObjectInputStream IN;
    private final ObjectOutputStream OUT;

    private String name;
    private boolean connected;
    private SecondaryController controller;

    public ChatClient(String name) throws IOException, LoginException {
        this.name = name;
        Socket socket = new Socket("localhost", 8187);
        OUT = new ObjectOutputStream((socket.getOutputStream()));
        OUT.flush();
        IN = new ObjectInputStream(socket.getInputStream());
        OUT.writeObject(new Token(name));
        OUT.flush();
        try {
            Token login = (Token) IN.readObject();
            if (!login.isPermitted()) throw new LoginException(login.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connected = true;
    }

    public void sendMessage (Message m) throws IOException {
        OUT.writeObject(m.withAuthor(name));
    }

    public void receiveTo() {
        Thread receiver = new Thread(() -> {
            while (connected) {
                try {
                    Message m = (Message) IN.readObject();
                    boolean isMyMessage = m.getAuthor().equals(name);
                    Platform.runLater(() -> controller.receive(m, isMyMessage));
                } catch (IOException e) {
                    connected = false;
                } catch (ClassNotFoundException ignored) {}
            }
        });
        receiver.setDaemon(true);
        receiver.start();
    }

    public void setController(SecondaryController controller) {
        this.controller = controller;
    }
}
