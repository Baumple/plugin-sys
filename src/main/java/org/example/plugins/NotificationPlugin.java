package org.example.plugins;

public interface NotificationPlugin {
    void sendNotification(String message);

    String getName();
}
