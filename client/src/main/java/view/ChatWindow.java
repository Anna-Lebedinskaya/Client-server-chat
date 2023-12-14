package ru.cft.focus.view;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focus.common.connection.ConnectionListener;
import ru.cft.focus.common.message.*;
import ru.cft.focus.connection.ClientConnection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class ChatWindow extends JFrame implements ConnectionListener<ClientConnection> {
    private final int WIDTH = 600;
    private final int HEIGHT = 400;

    private final JTextArea dialog;
    private final JTextArea users;
    private final JTextField userName;
    private final JTextField input;
    private final ErrorConnectionWindow errorConnectionWindow;
    private ClientConnection clientConnection;

    public ChatWindow(ErrorConnectionWindow errorConnectionWindow) {
        super("Chat");

        this.errorConnectionWindow = errorConnectionWindow;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        dialog = new JTextArea(15, 28);
        users = new JTextArea(15, 12);
        userName = new JTextField();
        input = new JTextField(45);
        JButton sendButton = new JButton("Send");

        JPanel panel = new JPanel();

        panel.add(input);
        panel.add(sendButton);

        sendButton.addActionListener(e -> addInputListener());
        input.addActionListener(e -> addInputListener());

        dialog.setEditable(false);
        dialog.setLineWrap(true);
        users.setEditable(false);
        userName.setEditable(false);

        add(new JScrollPane(dialog), BorderLayout.CENTER);
        add(new JScrollPane(users), BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);
        add(userName, BorderLayout.NORTH);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (clientConnection != null) {
                disconnected(clientConnection);
            }
        }));
    }

    private void addInputListener() {
        String message = input.getText();
        if (!message.equals("")) {
            input.setText(null);
            if (clientConnection != null) {
                clientConnection.sendMessage(new MessageRequest(message, userName.getText()));
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                String data = sdf.format(new Date(System.currentTimeMillis()));
                printMessage(" " + userName.getText() + " " + data + ": " + message);
            }
        }
    }

    public boolean connectToServer(String address, String port) {
        if (address == null || port == null) {
            return false;
        }
        int portInteger;
        try {
            portInteger = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            log.error("The entered port is not a number: {}, {}", port, e.getMessage());
            return false;
        }
        try {
            clientConnection = new ClientConnection(this, address, portInteger);
            clientConnection.start();
        } catch (IllegalArgumentException | IOException e) {
            log.error("Address {} or port {} is invalid, {}", address, port, e.getMessage());
            return false;
        }
        return true;
    }

    public void setUserName(String value) {
        userName.setText(value);
    }

    @Override
    public void connected(ClientConnection connection) {
        String msg = userName.getText();
        if (!msg.isEmpty()) {
            connection.sendMessage(new AuthRequest(msg));
//            log.info("Отправлен запрос на авторизацию");
        }
    }

    @Override
    public void messageReceived(ClientConnection connection, Message message) {
        switch (message.getType()) {
            case INFO -> printMessage(" ----- " + ((Info) message).getText() + " ----- ");
            case MESSAGE_REQUEST -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                String data = sdf.format(new Date(message.getTimestamp()));
                printMessage(" " + ((MessageRequest) message).getUserName() + " " + data + ": "
                             + ((MessageRequest) message).getText());
            }
            case MESSAGE_NOTIFICATION -> printMessage(((MessageNotification) message).getText());
            case CONNECTION_REFUSED -> {
                errorConnectionWindow.setErrorLabel("This login is already in use!");
                errorConnectionWindow.setVisible(true);
            }
            case CONNECTION_ACCEPTED -> setVisible(true);
            case LIST_USERS -> printUserList(((ActiveUsersList) message).getUserList());
        }
    }

    private synchronized void printUserList(List<String> userList) {
        SwingUtilities.invokeLater(() -> {
            users.setText("");
            StringBuilder text = new StringBuilder("Connected users:\n");
            for (String user : userList) {
                text.append(user).append("\n");
            }
            users.append(text.toString());
        });
    }

    @Override
    public void disconnected(ClientConnection connection) {
        connection.sendMessage(new LogoutRequest(userName.getName()));
        connection.disconnect();
    }

    @Override
    public void handleException(ClientConnection connection, Exception exception) {
        setVisible(false);
        dialog.setText("");
        errorConnectionWindow.setErrorLabel("Connection lost on server side!");
        errorConnectionWindow.setVisible(true);
    }

    private synchronized void printMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            dialog.append(message + "\n");
            dialog.setCaretPosition(dialog.getDocument().getLength());
        });
    }
}
