package de.cooperr.cooperrsurvival.util;

import de.cooperr.cooperrsurvival.CooperrSurvival;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

public class Config extends YamlConfiguration {

    public Config(CooperrSurvival plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        try {
            file.createNewFile();
            load(file);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load config \"" + fileName + "\"!");
        }
    }
}
