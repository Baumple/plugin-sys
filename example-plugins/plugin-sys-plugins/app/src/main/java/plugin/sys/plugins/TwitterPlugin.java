public class TwitterPlugin implements notification.plugin.NotificationPlugin {
    public void sendNotification(String message) {
        System.out.println("Tweet: " + message);
    }

    public String getName() {
        return "Twitter Plugin";
    }
}
