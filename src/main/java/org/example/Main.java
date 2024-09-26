package org.example;

import org.example.plugins.PluginLoader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var loader = new PluginLoader();
        loader.loadPluginsFromJar(
                "./example-plugins/plugin-sys-plugins/app/build/libs/app.jar"
        );

        loader.getPlugins().forEach(p -> p.sendNotification("Hello"));
        sendMessage(loader);
        // new PluginGUI(loader.getPlugins().stream().toList());
    }

    private static void sendMessage(PluginLoader loader) {
        System.out.print("Enter message to send: ");
        try (var scanner = new Scanner(System.in)) {
            var msg = scanner.nextLine();
            loader.getPlugins().forEach(p -> p.sendNotification(msg));
        }
    }

}