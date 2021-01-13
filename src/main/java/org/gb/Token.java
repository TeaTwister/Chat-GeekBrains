package org.gb;

import java.io.Serializable;

public class Token implements Serializable {
    private String message;
    private boolean permit;

    public Token(boolean permit) {
        this.permit = permit;
    }

    public Token(String message) {
        this.message = message;
    }

    public Token(String message, boolean permit) {
        this.message = message;
        this.permit = permit;
    }

    public String getMessage() {
        return message;
    }

    public boolean isPermitted() {
        return permit;
    }
}
