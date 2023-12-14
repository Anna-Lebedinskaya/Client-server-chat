package ru.cft.focus.common.connection;

import ru.cft.focus.common.message.Message;

public interface ConnectionListener<T extends Connection<T>> {
    void connected(T connection); //

    void messageReceived(T connection, Message message);

    void disconnected(T connection);

    void handleException(T connection, Exception exception);
}
