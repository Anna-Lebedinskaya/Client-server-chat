package ru.cft.focus;

import ru.cft.focus.view.View;

import javax.swing.*;

public class AppClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new View();
        });
    }
}