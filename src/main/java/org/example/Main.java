package org.example;

import org.example.gui.PluginGUI;
import org.example.plugins.PluginLoader;

public class Main {

    public static void main(String[] args) {

        var loader = new PluginLoader(
                "./example-plugins/plugin-sys-plugins/app/build/libs/app.jar"
        );
        loader.getPlugins().forEach(p -> p.sendNotification("Hello"));
        new PluginGUI(loader.getPlugins().stream().toList());
    }

}