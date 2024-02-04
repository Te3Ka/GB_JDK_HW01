package org.example.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ServerWindow extends JFrame {
    private static final int POS_X = 600;
    private static final int POS_Y = 350;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    private final JButton buttonStart = new JButton("Start Server");
    private final JButton buttonStop = new JButton("Stop Server");
    private final JTextArea serverLogArea = new JTextArea();
    public boolean isServerRunning;

    LogFile logFile = new LogFile();

    public ServerWindow() throws FileNotFoundException {
        isServerRunning = false;
        updateLogArea();

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerRunning) {
                    String message = "Server already stopped.\n";
                    try {
                        logFile.write(message);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    serverLogArea.append(message);
                } else {
                    isServerRunning = false;
                    String message = "Server stopped.\n";
                    try {
                        logFile.write(message);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    serverLogArea.append(message);
                }
            }
        });

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerRunning) {
                    String message = "Server is already running.\n";
                    try {
                        logFile.write(message);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    serverLogArea.append(message);
                } else {
                    isServerRunning = true;
                    String message = "Server is running.\n";
                    try {
                        logFile.write(message);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    serverLogArea.append(message);
                    serverLogArea.setText(updateLogArea());
                }
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat Server");
        setAlwaysOnTop(true);

        JPanel textAreaPanel = new JPanel(new GridLayout(1, 1));
        textAreaPanel.add(serverLogArea);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.add(buttonStart);
        buttonsPanel.add(buttonStop);

        setLayout(new BorderLayout());
        add(textAreaPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }


    public void setNewMessage(String message) throws IOException {
        if (isServerRunning) {
            logFile.write(message);
            serverLogArea.append(message);
            serverLogArea.setText(updateLogArea());
        }
    }

    public String updateLogArea() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : logFile.read()) {
            stringBuilder.append(str);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
