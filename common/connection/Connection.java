package ru.cft.focus.common.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focus.common.message.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public abstract class Connection<T extends Connection<T>> {
    private final Socket socket;
    private final ConnectionListener<T> eventListener;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final ObjectMapper objectMapper;
    private Thread thread;

    protected Connection(ConnectionListener<T> eventListener, String ipAddr, int port)
            throws IllegalArgumentException, IOException {
        this(eventListener, new Socket(ipAddr, port));
    }

    protected Connection(ConnectionListener<T> eventListener, Socket socket) throws IOException {
        this.eventListener = eventListener;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        objectMapper = new ObjectMapper();
    }

    protected abstract T self();

    public void start() {
        thread = new Thread(() -> {
            try {
                eventListener.connected(self());
                String value;
                while (!thread.isInterrupted() && (value = in.readLine()) != null) {
                    Message message;
                    message = objectMapper.readValue(value, Message.class);
                    eventListener.messageReceived(self(), message);
                }
            } catch (IOException e) {
                eventListener.handleException(self(), e);
            } finally {
                eventListener.disconnected(self());
            }
        });
        thread.start();
    }

    public synchronized void sendMessage(Message message) {
        try {
            out.write(objectMapper.writeValueAsString(message) + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListener.handleException(self(), e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        thread.interrupt();
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            eventListener.handleException(self(), e);
        }
    }

    @Override
    public String toString() {
        return "Connection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
