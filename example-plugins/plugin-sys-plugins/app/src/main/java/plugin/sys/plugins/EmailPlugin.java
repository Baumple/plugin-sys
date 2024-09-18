package plugin.sys.plugins;

public class EmailPlugin implements org.example.plugins.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("Email: " + message);
    }
}
