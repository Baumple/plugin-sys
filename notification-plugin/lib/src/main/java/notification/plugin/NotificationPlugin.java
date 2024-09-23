package notification.plugin;

import javax.swing.*;

import com.moandjiezana.toml.Toml;

public interface NotificationPlugin {
    void setConfig(Toml toml);

    void sendNotification(String message);

    String getName();
}
