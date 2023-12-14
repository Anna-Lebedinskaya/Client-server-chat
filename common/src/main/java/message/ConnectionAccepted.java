package ru.cft.focus.common.message;

public class ConnectionAccepted extends Message {
    public ConnectionAccepted() {
        super(System.currentTimeMillis());
    }

    @Override
    public MessageType getType() {
        return MessageType.CONNECTION_ACCEPTED;
    }
}