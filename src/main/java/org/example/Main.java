package org.example;

import org.example.gui.Application;
import org.example.plugins.PluginLoader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Application(400, 400);
    }

    private static void sendMessage(PluginLoader loader) {
        System.out.print("Enter message to send: ");
        try (var scanner = new Scanner(System.in)) {
            var msg = scanner.nextLine();
            loader.getPlugins().forEach(p -> p.sendNotification(msg));
        }
    }

}