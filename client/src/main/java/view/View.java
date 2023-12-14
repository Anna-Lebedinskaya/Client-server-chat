package ru.cft.focus.view;

import lombok.Getter;

@Getter
public class View {
    private final ConnectionWindow connectionWindow;
    private final ErrorConnectionWindow errorConnectionWindow;
    private final ChatWindow chatWindow;

    public View() {
        errorConnectionWindow = new ErrorConnectionWindow();
        chatWindow = new ChatWindow(errorConnectionWindow);
        connectionWindow = new ConnectionWindow();

        connectionWindow.setConnectionListener(e -> {
            String address = connectionWindow.getAddress();
            String port = connectionWindow.getPort();
            String login = connectionWindow.getLogin();
            chatWindow.setUserName(login);
            boolean connect = chatWindow.connectToServer(address, port);
            if (!connect) {
                errorConnectionWindow.setErrorLabel("This address or port is available!");
                errorConnectionWindow.setVisible(true);
            }
        });
        connectionWindow.setExitListener(e -> chatWindow.dispose());

        errorConnectionWindow.setConnectionListener(e -> connectionWindow.setVisible(true));
        errorConnectionWindow.setExitListener(e -> chatWindow.dispose());
    }
}

