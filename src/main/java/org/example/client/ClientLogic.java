package org.example.client;

import org.example.server.ServerWindow;

public class ClientLogic implements ClientView {

    ServerWindow serverWindow;
    ClientGUI clientGUI;

    public ClientLogic(ServerWindow serverWindow, ClientGUI clientGUI) {
        this.serverWindow = serverWindow;
        this.clientGUI = clientGUI;
    }

    public void updateTextArea(ServerWindow serverWindow) {
        while (clientGUI.isConnected) {
            clientGUI.logArea.setText(serverWindow.updateLogArea());
        }
    }
}
