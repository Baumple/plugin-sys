package plugin.sys.plugins;

public class SMSPlugin implements notification.plugin.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("SMS: " + message);
    }

    public String getName() {
        return "SMS Plugin";
    }
}
