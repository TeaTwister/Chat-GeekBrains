package org.gb;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String author;
    private String text;
    private LocalDateTime timeStamp;

    public static Message make() {
        Message m = new Message();
        m.timeStamp = LocalDateTime.now();
        return m;
    }

    public Message withText(String text) {
        this.text = text;
        return this;
    }

    public Message withAuthor(String name) {
        this.author = name;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
