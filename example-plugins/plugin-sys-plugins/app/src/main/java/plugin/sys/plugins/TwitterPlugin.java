public class TwitterPlugin implements org.example.plugins.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("Tweet: " + message);
    }

    public String getName() {
        return "Twitter Plugin";
    }
}
