package org.example.plugins;

import notification.plugin.NotificationPlugin;
import org.example.plugins.results.PluginCreationResult;
import org.example.plugins.results.PluginLoadError;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PluginLoader {
    private final Map<String, NotificationPlugin> loadedPlugins = new HashMap<>();

    public Optional<PluginLoadError> loadPluginsFromJar(String pathToJar) {
        // Absolute Path required for the URLClassLoader
        var path = Path.of(pathToJar).toAbsolutePath().toString();
        System.out.println("Reading jar at: " + path);

        var plugins = listPlugins(path);
        try (URLClassLoader loader = URLClassLoader.newInstance(
                new URL[]{new URL("file:///" + path)},
                getClass().getClassLoader()
        )) {
            var loadResults = plugins.stream()
                    .map(name ->
                            PluginFactory.createPlugin(loader, name)
                    )
                    .toList();

            var errors = new ArrayList<PluginCreationResult>(List.of());
            for (var result : loadResults) {
                if (result instanceof PluginCreationResult.Ok ok) {
                    loadedPlugins.put(ok.plugin.getClass().getName(), ok.plugin);
                } else {
                    errors.add(result);
                }
            }

            if (errors.isEmpty())
                return Optional.empty();
            else
                return Optional.of(new PluginLoadError.PluginCreationError(errors));

        } catch (IOException e) {
            System.out.println("Failed to load a Plugin:\n");
            return Optional.of(new PluginLoadError.IOError(e));
        }
    }

    public List<NotificationPlugin> getPlugins() {
        return this.loadedPlugins
                .values()
                .stream()
                .toList();
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
                    String className = entry.getName().replace('/', '.' ); // including ".class"
                    classNames.add(className.substring(0, className.length() - ".class".length()));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read Jar file at '" + pathToJar + "'");
        }

        return classNames;
    }


}
