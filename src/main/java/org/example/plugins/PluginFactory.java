package org.example.plugins;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Optional;

public class PluginFactory {
    private final ClassLoader classLoader;

    public PluginFactory(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Create an instance of a Java class satisfying the {@link NotificationPlugin} interface.
     * @param name Fully qualified name of the Java class (i.e. "org.example.SMSPlugin")
     * @return An Empty Optional if the plugin could not be loaded or does not satisfy the interface.
     */
    public static Optional<NotificationPlugin> createPlugin(ClassLoader loader, String name) {
        try {
            var loaded = loader.loadClass(name);
            var interfaces = loaded.getInterfaces();

            if (!Arrays.asList(interfaces).contains(NotificationPlugin.class)) {
                return Optional.empty();
            }

            var instance = (NotificationPlugin) loaded.getDeclaredConstructor().newInstance();
            return Optional.of(instance);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Create an instance of a Java class satisfying the {@link NotificationPlugin} interface.
     * @param name Fully qualified name of the Java class (i.e. "org.example.SMSPlugin")
     * @return An Empty Optional if the plugin could not be loaded or does not satisfy the interface.
     */
    public Optional<NotificationPlugin> createPlugin(String name) {
        return createPlugin(this.classLoader, name);
    }
}
