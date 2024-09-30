# Aufgabe:

Entwicklung eines dynamischen Plugin-Systems mit Java-Interfaces
und dem Factory Design-Pattern.

## Setup

### Plugin-Entwicklung

Um Plugins zu "entwickeln" benötigt man das NotificationPlugin interface.
In der Subdirectory "notification-plugin" befindet sich ein Gradle-Projekt,
indem das interface liegt. Die kompilierte Jar-Datei kann man dann in einem separaten
Gradle-Projekt einbetten und so eine Klasse, die das Interface implementiert erstellen.
Ein Beispiel dafür findet sich in der example-plugins directory.

Bsp. (mit gradle):

```kotlin
// app/build.gradle.kts

dependencies {
    implementation(files("path/to/notification-plugin-jar"))
    // ...
}
// ...
```

### Verwendung

Das Interface kann nun im Projekt verwendet werden:

```java
import notification.plugin.NotificationPlugin;

public class SMSPlugin implements NotificationPlugin {
    public void sendNotification(String message) {
        // ...
    }
    // ...
}
```

Um die Implementationen verwenden zu können muss man nun die Plugin-Klassen
zu einer Jar-Datei kompilieren.

Das Laden der Plugins übernimmt dann der PluginLoader.
