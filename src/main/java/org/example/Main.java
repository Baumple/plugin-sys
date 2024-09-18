package org.example;

import org.example.plugins.PluginLoader;

public class Main {
    public static void main(String[] args) {
        var loader = new PluginLoader(
                "./examples/plugin-sys-plugins/app/build/libs/app.jar"
        );

        loader.getPlugins().forEach(p -> p.sendNotification("Hello"));
    }
}