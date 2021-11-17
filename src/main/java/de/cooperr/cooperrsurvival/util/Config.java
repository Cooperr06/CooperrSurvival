package de.cooperr.cooperrsurvival.util;

import de.cooperr.cooperrsurvival.CooperrSurvival;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config extends YamlConfiguration {

    private final CooperrSurvival plugin;

    public Config(CooperrSurvival plugin) {
        this.plugin = plugin;
    }
}
