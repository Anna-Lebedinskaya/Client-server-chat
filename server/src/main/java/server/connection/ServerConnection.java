package ru.cft.focus.server.connection;

import ru.cft.focus.common.connection.Connection;
import ru.cft.focus.common.connection.ConnectionListener;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Connection<ServerConnection> {
    private String login;
    private boolean isAuthorised;

    public ServerConnection(ConnectionListener<ServerConnection> eventListener, Socket socket)
            throws IOException {
        super(eventListener, socket);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAuthorised() {
        return isAuthorised;
    }

    public void setAuthorised(boolean authorised) {
        isAuthorised = authorised;
    }

    @Override
    public ServerConnection self() {
        return this;
    }
}
