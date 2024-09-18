package plugin;

public class AppPlugin implements org.example.plugins.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("App: " + message);
    }
}
