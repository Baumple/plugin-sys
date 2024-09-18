package plugin.sys.plugins;

public class SMSPlugin implements org.example.plugins.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("SMS: " + message);
    }
}
