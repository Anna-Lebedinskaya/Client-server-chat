package ru.cft.focus.common.message;

public class ConnectionRefused extends Message {
    public ConnectionRefused() {
        super(System.currentTimeMillis());
    }

    @Override
    public MessageType getType() {
        return MessageType.CONNECTION_REFUSED;
    }
}
