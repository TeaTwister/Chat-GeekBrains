package org.gb;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.ServerSocket;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

    private static final HashMap<String, ChatClientHandler> users = new HashMap<>();

    public ChatServer() {
        try (final ServerSocket SERVER = new ServerSocket(8187)) {
            while (true) {
                ChatClientHandler client;
                try {
                    client = new ChatClientHandler(SERVER.accept(), this);
                } catch (LoginException e) {
                    continue;
                }
                Thread clientThread = new Thread(client);
                clientThread.setDaemon(true);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean signIn(String name, ChatClientHandler handler) {
        if (users.containsKey(name)) return false;
        users.putIfAbsent(name, handler);
        System.out.println("Signed in " + name);
        return true;
    }

    public void signOut(String name) {
        users.remove(name);
        System.out.println("Signed out " + name);
    }

    public void broadcast(Message m) {
        System.out.println(
                m.getAuthor() + ": "
                + m.getText() + " @ "
                + m.getTimeStamp().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        );
        for (Map.Entry<String, ChatClientHandler> user : users.entrySet()) {
            String name = user.getKey();
            ChatClientHandler handler = user.getValue();
            try {
                handler.transmit(m);
            } catch (IOException e) {
                e.printStackTrace();
                signOut(name);
            }
        }
    }

    public static void main(String[] args) {
        new ChatServer();
    }
}
