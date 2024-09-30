package org.example.plugins;

import notification.plugin.NotificationPlugin;

import java.util.function.Consumer;

public sealed class PluginLoadResult {
    public static void peekOk(PluginLoadResult result, Consumer<Loaded> f) {
        if (result instanceof Loaded loaded) {
            f.accept(loaded);
        }
    }

    public static Loaded getOk(PluginLoadResult result) {
        return (Loaded) result;
    }

    public static boolean isOk(PluginLoadResult r) {
        return r instanceof Loaded;
    }

    public static final class Loaded extends PluginLoadResult {
        public final NotificationPlugin plugin;

        public Loaded(NotificationPlugin plugin) {
            this.plugin = plugin;
        }
    }

    public static final class CouldNotInstantiate extends PluginLoadResult {
        public final InstantiationException error;

        public CouldNotInstantiate(InstantiationException e) {
            this.error = e;
        }
    }

    public static final class MissingDefaultConstructor extends PluginLoadResult {
        public final NoSuchMethodException error;

        public MissingDefaultConstructor(NoSuchMethodException error) {
            this.error = error;
        }
    }

    public static final class MissingInterface extends PluginLoadResult {
    }

    public static final class OtherException extends PluginLoadResult {
        public final Throwable error;

        public OtherException(Throwable error) {
            this.error = error;
        }
    }
}
