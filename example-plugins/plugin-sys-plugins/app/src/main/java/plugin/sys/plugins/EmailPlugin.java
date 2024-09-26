package plugin.sys.plugins;

import java.util.Map;

public class EmailPlugin implements notification.plugin.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("Email: " + message);
    }


    public void setConfig(Map<String, Object> config) {
    }

}
