package org.example

import com.moandjiezana.toml.Toml
import java.io.File

const val CONFIG_DIR = "./configs"

sealed class TomlResult {
    data class Success(val config: Toml) : TomlResult()
    data class ReadConfigError(val exception: IllegalStateException) : TomlResult()

    data class InvalidConfigError(val message: String) : TomlResult()

}

private fun readConfig(path: String): TomlResult =
    try {
        TomlResult.Success(Toml().read(File(path)))
    } catch (e: IllegalStateException) {
        TomlResult.ReadConfigError(e)
    }

fun loadConfig(pluginName: String): TomlResult {
    val config =
        when (val config = readConfig("$CONFIG_DIR/$pluginName.toml")) {
            is TomlResult.Success -> config.config
            else -> return config
        }

    if (config.contains("plugin_config"))
        return TomlResult.InvalidConfigError("Field 'plugin_config' is not set.")

    return TomlResult.Success(config)
}

/**
 * Returns a list of TomlResults
 */
fun readAllConfigs(): List<TomlResult> {
    val files =
        File(CONFIG_DIR).listFiles()
            ?.filter(File::isDirectory) ?: return listOf()
    return files.map { readConfig(it.absolutePath) }
}