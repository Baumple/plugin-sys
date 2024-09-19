package plugin.sys.plugins;

public class EmailPlugin implements notification.plugin.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("Email: " + message);
    }

    public String getName() {
        return "Email Plugin";
    }
}
