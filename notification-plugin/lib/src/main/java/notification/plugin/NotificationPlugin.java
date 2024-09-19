package notification.plugin;

import javax.swing.*;

public interface NotificationPlugin {
    void sendNotification(String message);

    String getName();
}
