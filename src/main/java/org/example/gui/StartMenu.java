package org.example.gui;

import notification.plugin.NotificationPlugin;
import org.example.plugins.PluginLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StartMenu extends Container {
    public StartMenu(Map<NotificationPlugin, Boolean> enabledMap, PluginLoader loader) {
        this.setLayout(new BorderLayout());
        var titleLabel = new JLabel("Write a Message");
        titleLabel.setFont(Application.HEADER_FONT);

        var textArea = createMessageArea();

        var sendButton = new JButton("Send");
        sendButton.setFont(Application.DEFAULT_FONT);
        sendButton.addActionListener(e -> {
            var plugins = loader.getPlugins();
            if (plugins.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "No plugins loaded. Please load some plugins first.",
                        "No plugins loaded.",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            var enabled = plugins.stream()
                    .filter(plugin -> enabledMap.getOrDefault(plugin, false))
                    .toList();

            if (enabled.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "No plugins enabled. You have to enable at least one plugin.",
                        "No plugins enabled.",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            enabled.forEach(p -> p.sendNotification(textArea.getText()));
            textArea.setText("");
        });

        this.add(titleLabel, BorderLayout.NORTH);
        this.add(textArea, BorderLayout.CENTER);
        this.add(sendButton, BorderLayout.SOUTH);
    }

    private JTextArea createMessageArea() {
        var textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setFont(Application.DEFAULT_FONT);

        return textArea;
    }
}
