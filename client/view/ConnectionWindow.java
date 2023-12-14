package ru.cft.focus.view;

import javax.swing.*;
import java.awt.event.ActionListener;


public class ConnectionWindow extends JFrame {
    private final int WIDTH = 265;
    private final int HEIGHT = 230;

    private final JTextField address;
    private final JTextField port;
    private final JTextField login;

    private ActionListener connectionListener;
    private ActionListener exitListener;

    public ConnectionWindow() {
        super("Connection");

        JLabel addressLabel = new JLabel("Address");
        addressLabel.setBounds(15, 15, 50, 30);
        add(addressLabel);

        address = new JTextField("localhost");
        address.setBounds(80, 15, 150, 30);
        add(address);

        JLabel portLabel = new JLabel("Port");
        portLabel.setBounds(15, 55, 50, 30);
        add(portLabel);

        port = new JTextField("8189");
        port.setBounds(80, 55, 150, 30);
        add(port);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setBounds(15, 100, 50, 30);
        add(loginLabel);

        login = new JTextField("test");
        login.setBounds(80, 100, 150, 30);
        add(login);

        JButton connectionButton = new JButton("Connect");
        connectionButton.setBounds(15, 145, 100, 30);
        connectionButton.addActionListener(e -> {
            dispose();
            if (connectionListener != null) {
                connectionListener.actionPerformed(e);
            }
        });
        add(connectionButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(130, 145, 100, 30);
        exitButton.addActionListener(e -> {
            dispose();

            if (exitListener != null) {
                exitListener.actionPerformed(e);
            }
        });
        add(exitButton);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setConnectionListener(ActionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

    public void setExitListener(ActionListener exitListener) {
        this.exitListener = exitListener;
    }

    public String getAddress() {
        return address.getText();
    }

    public String getPort() {
        return port.getText();
    }

    public String getLogin() {
        return login.getText();
    }
}
