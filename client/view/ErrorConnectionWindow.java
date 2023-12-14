package ru.cft.focus.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ErrorConnectionWindow extends JFrame {
    private final int WIDTH = 265;
    private final int HEIGHT = 140;
    private JLabel errorLabel;
    private ActionListener connectionListener;

    private ActionListener exitListener;

    public ErrorConnectionWindow() {
        super("Error");

        errorLabel = new JLabel("This address or port is available!");
        errorLabel.setBounds(30, 5, 210, 20);
        add(errorLabel);

        JLabel tryLabel = new JLabel("Try again?");
        tryLabel.setBounds(90, 25, 210, 20);
        add(tryLabel);

        JButton connectionButton = new JButton("Yes");
        connectionButton.setBounds(15, 55, 100, 30);
        connectionButton.addActionListener(e -> {
            dispose();
            if (connectionListener != null) {
                connectionListener.actionPerformed(e);
            }
        });
        add(connectionButton);

        JButton exitButton = new JButton("No");
        exitButton.setBounds(130, 55, 100, 30);
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
    }

    public void setErrorLabel(String label) {
        this.errorLabel.setText(label);
    }

    public void setConnectionListener(ActionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

    public void setExitListener(ActionListener exitListener) {
        this.exitListener = exitListener;
    }
}
