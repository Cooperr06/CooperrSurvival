package de.cooperr.cooperrsurvival.util;

import de.cooperr.cooperrsurvival.CooperrSurvival;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

public class Config extends YamlConfiguration {

    private final CooperrSurvival plugin;
    @Getter
    private final File file;

    public Config(CooperrSurvival plugin, String fileName) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            try {
                Files.copy(Paths.get("./resources/" + fileName.substring(0, fileName.length() - 5) + "ConfigTemplate.yml"), new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            plugin.getLogger().log(Level.FINE, "Copied config defaults for config \"" + fileName + "\"!");
        }

        try {
            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void set(@NotNull String path, @Nullable Object value) {
        super.set(path, value);
        try {
            save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to save config \"" + file.getName() + "\"!");
        }
    }
}
