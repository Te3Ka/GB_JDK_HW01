package org.example.server;

import java.io.IOException;

public class ServerLogic implements ServerView {
    LogFile logFile;
    ServerWindow serverWindow;

    public ServerLogic(LogFile logFile, ServerWindow serverWindow) {
        this.logFile = logFile;
        this.serverWindow = serverWindow;
    }

    public String updateLogArea() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : logFile.read()) {
            stringBuilder.append(str);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void setNewMessage(String message) throws IOException {
        if (serverWindow.isServerRunning) {
            logFile.write(message);
            serverWindow.serverLogArea.append(message);
            serverWindow.serverLogArea.setText(updateLogArea());
        }
    }
}
