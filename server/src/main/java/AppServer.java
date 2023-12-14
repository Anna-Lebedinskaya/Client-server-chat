package ru.cft.focus;

import ru.cft.focus.server.Server;
import ru.cft.focus.server.utils.ApplicationProperties;

public class AppServer {
    public static void main(String[] args) {
        ApplicationProperties.loadProperties();
        int port = ApplicationProperties.getPort();
        Server server = new Server(port);
        Runtime.getRuntime().addShutdownHook(new Thread("shutdown") {
            @Override
            public void run() {
                server.shutdown();
            }
        });
        server.startServer();
    }
}
