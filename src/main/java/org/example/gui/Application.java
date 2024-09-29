package org.example.gui;

import notification.plugin.NotificationPlugin;
import org.example.plugins.PluginLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Application extends JFrame {
    public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 24);
    public static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 48);
    private final PluginLoader loader = new PluginLoader();

    private final HashMap<NotificationPlugin, Boolean> enabledMap = new HashMap<>();

    private final PluginManager pluginManagerView = new PluginManager(enabledMap, loader);

    private final StartMenu startMenuView = new StartMenu(enabledMap, loader);

    public Application(int width, int height) {
        this.setSize(new Dimension(width, height));
        this.setLayout(new BorderLayout());

        this.setJMenuBar(createMenuBar());
        this.add(startMenuView, BorderLayout.CENTER);

        this.setVisible(true);
    }

    /**
     * @param e Not actually used, required for fancy method
     *          reference syntax when using as ActionListener callback.
     */
    private void moveToStart(Object e) {
        this.setContentPane(startMenuView);
        this.repaint();
        this.revalidate();
    }

    private void moveToPluginManager(Object e) {
        this.setContentPane(pluginManagerView);
        this.repaint();
        this.revalidate();
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var menu = new JMenu("Navigation");

        var startItem = new JMenuItem("Start");
        startItem.setToolTipText("Create and send a message.");

        var pluginItem = new JMenuItem("Plugin Manager");
        pluginItem.setToolTipText("Manage installed plugins.");

        startItem.addActionListener(this::moveToStart);

        pluginItem.addActionListener(this::moveToPluginManager);

        menu.add(startItem);
        menu.add(pluginItem);
        menuBar.add(menu);

        return menuBar;
    }
}
