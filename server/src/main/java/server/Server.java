package ru.cft.focus.server;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focus.common.connection.ConnectionListener;
import ru.cft.focus.common.message.*;
import ru.cft.focus.server.connection.ServerConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Server implements ConnectionListener<ServerConnection> {
    private final ServerSocket serverSocket;
    private final List<ServerConnection> allConnections = new ArrayList<>();

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            log.info("Server running");
        } catch (IOException e) {
            log.error("RuntimeException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        try {
            for (ServerConnection c : allConnections) {
                c.disconnect();
                log.info("Disconnect: {}, caused by server stopped", c);
            }
            serverSocket.close();
            log.info("Server stopped");
        } catch (IOException ignored) {
        }
    }

    public void startServer() {
        while (true) {
            try {
                ServerConnection serverConnection = new ServerConnection(this, serverSocket.accept());
                serverConnection.start();
            } catch (IOException e) {
                log.error("Connection exception: {}", e.getMessage());
            }
        }
    }

    @Override
    public synchronized void connected(ServerConnection connection) {
        allConnections.add(connection);
        log.info("Added new connection: {}", connection);
    }

    private List<String> calculateUserList() {
//        allConnections.stream().anyMatch(c->c.getUserName().equals(authRequest.getUserName())))
        List<String> users = new ArrayList<>();
        for (ServerConnection connection : allConnections) {
            if (connection.isAuthorised()) {
                users.add(connection.getLogin());
            }
        }
        return users;
    }

    private boolean isAuthorized(String login) {
        List<String> users = calculateUserList();
        return users.contains(login);
    }

    @Override
    public synchronized void messageReceived(ServerConnection connection, Message message) {
        if (!(connection).isAuthorised()) {
            if (!(message instanceof AuthRequest authRequest)) {
//                connection.sendMessage(new ErrorResponse(100500, "Auth required"));
                return;
            }
            if (isAuthorized(authRequest.getUserName())) {
                connection.sendMessage(new ConnectionRefused());
                disconnected(connection);
                log.info("Connection refused, disconnect: {}", connection);
                return;
            }
            connection.sendMessage(new ConnectionAccepted());
//            log.info("Запрос на авторизацию подтвержден");
            connection.setLogin(authRequest.getUserName());
            connection.setAuthorised(true);
            sendToAllConnections(new Info(authRequest.getUserName() + " has connected to the chat"));
            sendUserList();
        } else {
            switch (message.getType()) {
                case LOGOUT -> disconnected(connection);
                case MESSAGE_REQUEST -> sendToAllExceptOneConnection(connection, message);
            }
        }
    }

    private synchronized void sendUserList() {
        List<String> userList = calculateUserList();
        sendToAllConnections(new ActiveUsersList(userList));
    }

    @Override
    public synchronized void disconnected(ServerConnection connection) {
        if (allConnections.contains(connection) && connection.isAuthorised()) {
            Message message = new Info(connection.getLogin() + " left the chat");
            sendToAllConnections(message);
        }
        allConnections.remove(connection);
        log.info("Disconnect: {}", connection);
        sendUserList();
    }

    @Override
    public synchronized void handleException(ServerConnection connection, Exception exception) {
        log.error("Connection exception: {}", exception.getMessage());
    }

    private void sendToAllExceptOneConnection(ServerConnection connection, Message message) {
        for (ServerConnection c : allConnections) {
            if (c != connection) {
                c.sendMessage(message);
            } else {
                c.sendMessage(new MessageNotification("(received)"));
            }
        }
    }

    private void sendToAllConnections(Message message) {
        for (ServerConnection c : allConnections) {
            c.sendMessage(message);
        }
    }
}
