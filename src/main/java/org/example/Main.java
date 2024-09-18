package org.example;

import org.example.plugins.PluginLoader;
import org.example.plugins.gui.PluginGUI;

public class Main {
    public static void main(String[] args) {
        var loader = new PluginLoader(
                "./example-plugins/plugin-sys-plugins/app/build/libs/app.jar"
        );
        loader.getPlugins().forEach(p -> p.sendNotification("Hello"));
        new PluginGUI(loader.getPlugins().stream().toList());
    }
}