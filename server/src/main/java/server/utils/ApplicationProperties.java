package ru.cft.focus.server.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static ru.cft.focus.server.constants.PathConstants.PATH_PROPERTIES;

@Slf4j
public class ApplicationProperties {
    private static int port;

    private ApplicationProperties() {
    }

    public static void loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(PATH_PROPERTIES));
            log.info("Properties loaded successfully");
        } catch (IOException e) {
            log.error("Error in reading properties: {}", e.getMessage());
        }

        port = Integer.parseInt(properties.getProperty("port", "8189"));
    }

    public static int getPort() {
        return port;
    }

}