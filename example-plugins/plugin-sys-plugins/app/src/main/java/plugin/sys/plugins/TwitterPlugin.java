package plugin.sys.plugins;

import java.util.Map;

public class TwitterPlugin implements notification.plugin.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("Tweet: " + message);
    }

    public void setConfig(Map<String, Object> config) {
    }
}
