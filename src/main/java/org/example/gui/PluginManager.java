package org.example.gui;

import notification.plugin.NotificationPlugin;
import org.example.plugins.PluginLoader;
import org.example.plugins.results.PluginCreationResult;
import org.example.plugins.results.PluginLoadError;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PluginManager extends Container {
    // TODO: Save paths of loaded plugins
    private final ArrayList<String> pluginPaths = new ArrayList<>();
    private final PluginLoader loader;
    private JScrollPane pluginPanel;

    public PluginManager(
            Map<NotificationPlugin, Boolean> enabledMap,
            PluginLoader loader
    ) {
        this.loader = loader;
        this.setLayout(new BorderLayout());

        var titleLabel = new JLabel("Plugin Manager");
        titleLabel.setFont(Application.HEADER_FONT);
        this.add(titleLabel, BorderLayout.NORTH);

        this.pluginPanel = createPluginPane(enabledMap, loader.getPlugins());

        var addPluginsButton = createAddPluginsButton(enabledMap);

        this.add(this.pluginPanel, BorderLayout.CENTER);
        this.add(addPluginsButton, BorderLayout.SOUTH);
    }

    private JButton createAddPluginsButton(
            Map<NotificationPlugin, Boolean> enabledMap
    ) {
        var addPluginsButton = new JButton("Add plugins");

        addPluginsButton.addActionListener(e -> {
            var chooser = new JFileChooser();
            var filter = new FileNameExtensionFilter("Jar files", "jar");
            chooser.setFileFilter(filter);

            var approved = chooser.showOpenDialog(this);
            if (approved == JFileChooser.APPROVE_OPTION) {
                var file = chooser.getSelectedFile();
                this.addPlugins(enabledMap, file.getAbsolutePath());
            }
        });
        return addPluginsButton;
    }

    private void showErrorDialog(PluginLoadError loadError) {
        if (loadError instanceof PluginLoadError.IOError ioError) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load plugins from Jar:\n" + ioError.error.getMessage(),
                    "Could not load Jar",
                    JOptionPane.WARNING_MESSAGE
            );
        } else if (loadError instanceof PluginLoadError.PluginCreationError creationErrors) {
            var panel = createErrorPanel(creationErrors);
            JOptionPane.showMessageDialog(
                    this,
                    panel,
                    "Errors/Warnings while loading jar file",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void addPlugins(
            Map<NotificationPlugin, Boolean> enabledMap,
            String pathToJar
    ) {
        this.loader.loadPluginsFromJar(pathToJar).ifPresent(this::showErrorDialog);

        this.pluginPaths.add(pathToJar);

        this.remove(pluginPanel);
        this.pluginPanel = createPluginPane(enabledMap, this.loader.getPlugins());
        this.add(this.pluginPanel, BorderLayout.CENTER);

        this.repaint();
        this.revalidate();
    }

    private JScrollPane createPluginPane(
            Map<NotificationPlugin, Boolean> enabledMap,
            List<NotificationPlugin> plugins
    ) {
        var panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        var scrollPane = new JScrollPane(panel);

        for (var plugin : plugins) {
            panel.add(new PluginCard(enabledMap, plugin));
        }
        return scrollPane;
    }

    private JScrollPane createErrorPanel(PluginLoadError.PluginCreationError creationErrors) {
        var panel = new JPanel();
        panel.setLayout(new GridLayout(creationErrors.errorResults.size(), 0));
        var scrollPane = new JScrollPane(panel);
        for (PluginCreationResult errorResult : creationErrors.errorResults) {
            if (errorResult instanceof PluginCreationResult.MissingInterfaces interfaces) {
                var jtextField = new JTextField("Warning: class'" + interfaces.className + "' does not implement necessary interfaces.");
                jtextField.setEditable(false);
                jtextField.setForeground(Color.ORANGE);
                panel.add(jtextField);
            } else if (errorResult instanceof PluginCreationResult.OtherError other) {
                var jtextField = new JTextField("Error:\n" + other.error.getMessage());
                jtextField.setEditable(false);
                jtextField.setForeground(Color.RED);
                panel.add(jtextField);
            }
        }
        return scrollPane;
    }

}

class PluginCard extends JPanel {
    public PluginCard(
            Map<NotificationPlugin, Boolean> enabledMap,
            NotificationPlugin plugin
    ) {
        this.setLayout(new BorderLayout());

        var pluginName = new JLabel(plugin.getDisplayName());
        pluginName.setFont(Application.DEFAULT_FONT);
        this.add(pluginName, BorderLayout.NORTH);

        var pluginDesc = createPluginDescription(plugin.getDescription());
        this.add(pluginDesc, BorderLayout.CENTER);

        var btnText = "Enable";
        if (enabledMap.getOrDefault(plugin, false)) {
            btnText = "Disable";
        }

        var enableButton = createEnableButton(enabledMap, plugin, btnText);
        this.add(enableButton, BorderLayout.EAST);
    }

    private JTextArea createPluginDescription(String desc) {
        var pluginDesc = new JTextArea(desc);
        pluginDesc.setEditable(false);
        pluginDesc.setLineWrap(true);
        pluginDesc.setWrapStyleWord(true);
        pluginDesc.setBackground(Color.LIGHT_GRAY);
        pluginDesc.setFont(Application.DEFAULT_FONT.deriveFont(Font.ITALIC));
        return pluginDesc;
    }

    private JButton createEnableButton(Map<NotificationPlugin, Boolean> enabledMap, NotificationPlugin plugin, String btnText) {
        var enableButton = new JButton(btnText);
        enableButton.addActionListener(e -> {
            var enabled = !enabledMap.getOrDefault(plugin, false);
            enabledMap.put(plugin, enabled);

            if (enabled) {
                enableButton.setText("Disable");
            } else {
                enableButton.setText("Enabled");
            }
            enableButton.revalidate();
            enableButton.repaint();
        });
        return enableButton;
    }
}