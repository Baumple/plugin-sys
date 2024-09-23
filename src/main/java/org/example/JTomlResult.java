package org.example;

import com.moandjiezana.toml.Toml;

public sealed class JTomlResult {
    public final class Success extends JTomlResult {
        public final Toml toml;

        public Success(Toml toml) {
            this.toml = toml;
        }
    }

    public final class Failure extends JTomlResult {
        public final IllegalStateException error;

        public Failure(IllegalStateException error) {
            this.error = error;
        }
    }
}
