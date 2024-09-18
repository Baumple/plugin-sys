# Aufgabe:

Entwicklung eines dynamischen Plugin-Systems mit Java-Interfaces
und dem Factory Design-Pattern.

## Setup

### Plugin-Entwicklung

Um Plugins zu "entwickeln" kann man nun das Projekt kompilieren,
um eine Jar Datei zu erhalten, die das benötigte Interface `org.example.NotificationPlugin`
enthält. Dieses fügt man jetzt einem separaten Projekt hinzu und erstellt eine Klasse,
die dieses Interface implementiert.

Bsp. (mit gradle):

```kotlin
// app/build.gradle.kts

dependencies {
    implementation(files("path/to/local/jar"))
    // ...
}
// ...
```

Das Interface kann nun im Projekt verwendet werden:

```java
import org.example.NotificationPlugin;

public class SMSPlugin implements NotificationPlugin {
    public void sendNotification(String message) {
        // ...
    }
}
```

### Plugin-Verwendung

Um die Implementationen verwenden zu können muss man nun die Plugin-Klassen
zu einer Jar-Datei kompilieren.

Das Laden der Plugins in das Programm übernimmt schließlich die Klasse `PluginLoader` die mit einem Pfad
zu einer Jar-Datei mit den Plugins initialisiert wird. Die erfolgreich geladenen
Plugins kann man über die Methode `PluginLoader::getPlugins` erhalten.
