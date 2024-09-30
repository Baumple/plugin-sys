package notification.plugin;

import java.util.Map;

public interface NotificationPlugin {
    void sendNotification(String message);

    String getDescription();

    String getDisplayName();

    void setConfig(Map<String, Object> config);
}
