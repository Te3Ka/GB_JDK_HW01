package org.example.server;

import java.io.IOException;

public interface ServerView {
    String updateLogArea();
    void setNewMessage(String message) throws IOException;
}
