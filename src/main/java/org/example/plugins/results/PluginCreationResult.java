package org.example.plugins.results;

import notification.plugin.NotificationPlugin;

/**
 * The subtypes of this sealed classes specify the various errors that can occur when instantiating
 * a {@link NotificationPlugin}.
 */
public sealed class PluginCreationResult {
    /**
     * Contains fully initialized class.
     */
    public static final class Ok extends PluginCreationResult {
        public final NotificationPlugin plugin;

        public Ok(NotificationPlugin plugin) {
            this.plugin = plugin;
        }
    }

    /**
     * Class did not contain necessary interfaces.
     */
    public static final class MissingInterfaces extends PluginCreationResult {
        public final String className;

        public MissingInterfaces(String className) {
            this.className = className;
        }
    }

    /**
     * Another error occurred.
     */
    public static final class OtherError extends PluginCreationResult {
        public final Throwable error;

        public OtherError(Throwable error) {
            this.error = error;
        }
    }
}
