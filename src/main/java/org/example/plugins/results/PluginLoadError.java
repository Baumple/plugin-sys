package org.example.plugins.results;

import java.io.IOException;
import java.util.List;

/**
 * A sealed class where its subtypes represent possible errors when loading {@link notification.plugin.NotificationPlugin}s from a jar
 */
public sealed class PluginLoadError {
    /**
     * Jar file could not be found/read.
     */
    public static final class IOError extends PluginLoadError {
        public final IOException error;

        public IOError(IOException e) {
            this.error = e;
        }
    }

    /**
     * Contains a list of errors that happened during initializing classes of the jar file
     */
    public static final class PluginCreationError extends PluginLoadError {
        public final List<PluginCreationResult> errorResults;

        public PluginCreationError(List<PluginCreationResult> errorResults) {
            this.errorResults = errorResults;
        }
    }

}
