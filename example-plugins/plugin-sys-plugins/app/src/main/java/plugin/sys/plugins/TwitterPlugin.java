package plugin.sys.plugins;

import java.util.Map;

public class TwitterPlugin implements notification.plugin.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("Tweet: " + message);
    }

    public void setConfig(Map<String, Object> config) {
    }

    public String getDescription() {
        return "A plugin to send messages via a Tweet on Twitter.";
    }

    public String getDisplayName() {
        return "Twitter Plugin";
    }
}
