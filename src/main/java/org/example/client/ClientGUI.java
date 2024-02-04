package org.example.client;

import org.example.server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private final JTextArea logArea = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8080");
    private final JPanel tfEmpty = new JPanel();
    private final JTextField tfLogin = new JTextField("No Name");
    private final JPasswordField tfPassword = new JPasswordField("******");
    private final JButton buttonLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField tfMessage = new JTextField();
    private final JButton buttonSendMessage = new JButton("Send");
    private boolean isConnected;

    public ClientGUI(ServerWindow serverWindow) {
        isConnected = false;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Chat client");

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(tfEmpty);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(buttonLogin);
        add(panelTop, BorderLayout.NORTH);

        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(buttonSendMessage, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane);

        setVisible(true);
        setAlwaysOnTop(true);

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!serverWindow.isServerRunning) {
                    logArea.setText("Server not run");
                } else if (!isConnected) {
                    isConnected = true;
                    logArea.setText("Connected");
                    try {
                        serverWindow.setNewMessage(tfLogin.getText() + " is connected\n");
                        Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                if (isConnected || serverWindow.isServerRunning) {
                                    logArea.setText(serverWindow.updateLogArea());
                                }
                            }
                        };
                        timer.schedule(timerTask, 0, 1);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    logArea.setText("Already connected");
                }
            }
        });

        buttonSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isConnected) {
                    logArea.setText(tfLogin.getText() + ", you are not connected to the server.");
                } else {
                    String message = tfLogin.getText() + ": " + tfMessage.getText() + "\n";
                    try {
                        serverWindow.setNewMessage(message);
                        logArea.setText(serverWindow.updateLogArea());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    tfMessage.setText("");
                }
            }
        });

        tfMessage.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!isConnected) {
                        logArea.setText(tfLogin.getText() + ", you are not connected to the server.");
                    } else {
                        String message = tfLogin.getText() + ": " + tfMessage.getText() + "\n";
                        try {
                            serverWindow.setNewMessage(message);
                            logArea.setText(serverWindow.updateLogArea());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        tfMessage.setText("");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void updateTextArea(ServerWindow serverWindow) {
        while (isConnected) {
            logArea.setText(serverWindow.updateLogArea());
        }
    }
}
