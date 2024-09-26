package org.example.plugins;

import com.moandjiezana.toml.Toml;

import java.util.List;
import java.util.Map;

/**
 * A record containing both successful values and errors that occur when reading
 * the config toml files.
 *
 * @param configs Successfully read config files.
 * @param errors  Errors that occurred during reading.
 */
public record ConfigResult(Map<String, Toml> configs, List<TomlResult> errors) {
    public ConfigResult logResult() {
        if (!configs.isEmpty()) {
            System.err.println("Loaded configs:");
            for (var config : this.configs.keySet()) {
                System.err.println(config);
            }
        }

        if (!errors.isEmpty()) {
            System.err.println("Encountered errors: ");
            for (var error :
                    errors) {
                System.err.println(error);
            }

        }

        return this;
    }
}
