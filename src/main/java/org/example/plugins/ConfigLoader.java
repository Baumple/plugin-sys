package org.example.plugins;

import com.moandjiezana.toml.Toml;
import org.example.plugins.results.TomlConfigResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ConfigLoader {
    public static final String CONFIG_DIR = "./configs";

    public static TomlConfigResult loadConfig(String path) {
        try {
            var toml = new Toml().read(new File(path));
            if (!toml.contains("plugin_name")) {
                return new TomlConfigResult.MissingConfigName(path);
            }
            var name = toml.getString("plugin_name");
            return new TomlConfigResult.Config(name, toml);

        } catch (IllegalStateException e) {
            return new TomlConfigResult.InvalidToml(e, path);
        }
    }

    /**
     * Loads the configs located in a relative directory called 'config'.
     * If no such directory was found, the contents of {@link ConfigResult} will be empty.
     *
     * @return {@link ConfigResult} or null if no directory was found.
     */
    public static ConfigResult loadConfigs() {
        System.out.println("Loading configs");
        var dir = new File(CONFIG_DIR).listFiles();
        if (dir == null) {
            System.out.println("Config dir not found.");
            return new ConfigResult(new HashMap<>(), List.of());
        }

        ArrayList<TomlConfigResult> errors = new ArrayList<>();
        HashMap<String, Toml> configs = new HashMap<>();

        Arrays.stream(dir)
                .filter(File::isFile)
                .map(File::getAbsolutePath)
                .map(ConfigLoader::loadConfig)
                .forEach(it -> {
                    // if the config could be loaded successfully
                    if (it instanceof TomlConfigResult.Config config) {
                        configs.put(config.pluginName, config.toml);
                    } else {
                        // if the config could not be loaded successfully we add its error to the error stack.
                        errors.add(it);
                    }
                });

        return new ConfigResult(configs, errors);
    }

}
