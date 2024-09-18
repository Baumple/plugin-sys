package org.example;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

public class PluginLoader {
    HashMap<String, NotificationPlugin> plugins = new HashMap<>();

    public PluginLoader() {
        var dir = "/home/linusz/IdeaProjects/plugin-sys/plugins/build/build.jar";
        try (URLClassLoader loader = URLClassLoader.newInstance(
                new URL[]{new URL("file:///" + dir)},
                getClass().getClassLoader()
        )) {
            var loaded = loader.loadClass("SMSPlugin");
            System.out.println(loaded);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
