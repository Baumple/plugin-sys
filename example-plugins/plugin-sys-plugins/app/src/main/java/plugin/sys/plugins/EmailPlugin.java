package plugin.sys.plugins;

import java.util.Map;

public class EmailPlugin implements notification.plugin.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("Email: " + message);
    }


    public void setConfig(Map<String, Object> config) {
    }

    public String getDescription() {
        return "A plugin to send Messages via Email.";
    }

    public String getDisplayName() {
        return "Email Plugin";
    }
}
