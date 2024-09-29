package plugin.sys.plugins;

import java.util.Map;

public class SMSPlugin implements notification.plugin.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("SMS: " + message);
    }

    public void setConfig(Map<String, Object> config) {
    }

    public String getDescription() {
        return "A plugin to send Messages via SMS.";
    }

    public String getDisplayName() {
        return "SMS Plugin";
    }
}
