package org.example.plugins;

import com.moandjiezana.toml.Toml;

/**
 * A result returned when reading a TOML file.
 * Subtypes represent the different ways this could fail or a success value.
 */
public sealed class TomlResult {
    public static final class Config extends TomlResult {
        public final String pluginName;
        public final Toml toml;

        public Config(String pluginName, Toml toml) {
            this.pluginName = pluginName;
            this.toml = toml;
        }
    }

    public static final class MissingConfigName extends TomlResult {
        public final String path;

        public MissingConfigName(String path) {
            this.path = path;
        }
    }

    public static final class InvalidToml extends TomlResult {
        public final IllegalStateException exception;
        public final String path;

        public InvalidToml(IllegalStateException exception, String path) {
            this.exception = exception;
            this.path = path;
        }
    }
}
