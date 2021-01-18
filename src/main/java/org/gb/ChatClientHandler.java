package org.gb;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClientHandler implements Runnable {

    private final ChatServer SERVER;
    private final ObjectInputStream IN;
    private final ObjectOutputStream OUT;

    String name;

    public ChatClientHandler(Socket socket, ChatServer server)
            throws IOException, LoginException {
        SERVER = server;
        IN = new ObjectInputStream(socket.getInputStream());
        OUT = new ObjectOutputStream((socket.getOutputStream()));
        OUT.flush();
        try {
            Token login = (Token) IN.readObject();
            name = login.getMessage();
            if (server.signIn(name, this)) OUT.writeObject(new Token(true));
            else {
                OUT.writeObject(
                        new Token("There is already a user with same name", false));
                throw new LoginException();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = (Message) IN.readObject();
                SERVER.broadcast(message);
            } catch (IOException | ClassNotFoundException e) {
                SERVER.signOut(name);
                break;
            }
        }
    }

    public void transmit(Message m) throws IOException {
        OUT.writeObject(m);
        OUT.flush();
    }
}
