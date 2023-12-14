package ru.cft.focus.connection;

import ru.cft.focus.common.connection.Connection;
import ru.cft.focus.common.connection.ConnectionListener;

import java.io.IOException;

public class ClientConnection extends Connection<ClientConnection> {
    public ClientConnection(ConnectionListener<ClientConnection> eventListener, String ipAddr, int port)
            throws IllegalArgumentException, IOException {
        super(eventListener, ipAddr, port);
    }

    @Override
    protected ClientConnection self() {
        return this;
    }
}
