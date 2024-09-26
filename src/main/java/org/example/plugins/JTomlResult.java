package org.example.plugins;

import com.moandjiezana.toml.Toml;

public sealed class JTomlResult {
    public final class Success extends JTomlResult {
        public final Toml toml;

        public Success(Toml toml) {
            this.toml = toml;
        }
    }

    public final class ReadConfigError extends JTomlResult {
        IllegalStateException exception;

        public ReadConfigError(IllegalStateException exception) {
            this.exception = exception;
        }
    }

    public final class InvalidConfigError extends JTomlResult {
        public final String message;

        public InvalidConfigError(String message) {
            this.message = message;
        }
    }
}
