package org.example.plugins;

import com.moandjiezana.toml.Toml;
import notification.plugin.NotificationPlugin;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PluginLoader {
    private final List<TomlResult> errors = new ArrayList<>();
    private final Map<String, NotificationPlugin> loadedPlugins = new HashMap<>();

    public void loadPluginsFromJar(String pathToJar) {
        // Absolute Path required for the URLClassLoader
        var path = Path.of(pathToJar).toAbsolutePath().toString();
        System.out.println("Reading jar at: " + path);

        var configResult = ConfigLoader.loadConfigs().logResult();
        var configs = configResult.configs();
        this.errors.addAll(configResult.errors());

        var plugins = listPlugins(path);
        try (URLClassLoader loader = URLClassLoader.newInstance(
                new URL[]{new URL("file:///" + path)},
                getClass().getClassLoader()
        )) {
            plugins.stream()
                    .map(name -> PluginFactory.createPlugin(loader, name))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(plugin -> {
                        var absoluteName = plugin.getClass().getName();
                        System.err.println("Loaded plugin: " + absoluteName);

                        plugin.setConfig(configs.getOrDefault(absoluteName, new Toml()).toMap());

                        loadedPlugins.put(absoluteName, plugin);
                    });

        } catch (IOException e) {
            System.out.println("Failed to load a Plugin:\n");
            e.printStackTrace();
        }
    }

    public Collection<NotificationPlugin> getPlugins() {
        return this.loadedPlugins.values();
    }

    /**
     * Lists all classes in a jar file at the given path
     *
     * @param pathToJar The path to the jar file to read
     */
    private List<String> listPlugins(String pathToJar) {
        List<String> classNames = new ArrayList<String>();
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(pathToJar))) {
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace('/', '.'); // including ".class"
                    classNames.add(className.substring(0, className.length() - ".class".length()));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read Jar file at '" + pathToJar + "'");
        }

        return classNames;
    }
}
