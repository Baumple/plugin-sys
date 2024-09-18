package org.example;

import java.lang.reflect.*;

public class PluginFactory {
    public NotificationPlugin createPlugin(String directory, String name) {
        try {
            return (NotificationPlugin) this.getClass().getClassLoader().loadClass(name).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
