package notification.plugin;

import java.util.Map;

import com.moandjiezana.toml.Toml;

public interface NotificationPlugin {
    void sendNotification(String message);

    void setConfig(Map<String, Object> config);
}
