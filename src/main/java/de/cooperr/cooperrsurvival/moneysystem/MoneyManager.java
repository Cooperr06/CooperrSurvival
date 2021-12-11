package de.cooperr.cooperrsurvival.moneysystem;

import de.cooperr.cooperrsurvival.CooperrSurvival;
import de.cooperr.cooperrsurvival.util.MySqlConnection;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.logging.Level;

@Getter
public class MoneyManager {

    private final CooperrSurvival plugin;
    private final MySqlConnection connection;

    public MoneyManager(CooperrSurvival plugin) {
        this.plugin = plugin;

        ConfigurationSection settings = plugin.getSettingsConfig().getConfigurationSection("mysql");
        if (settings == null) {
            plugin.getLogger().log(Level.SEVERE, "Cannot find mysql data in config!");
            connection = null;
            return;
        }

        connection = new MySqlConnection(
                plugin,
                settings.getString("host"),
                settings.getInt("port"),
                settings.getString("database"),
                settings.getString("user"),
                settings.getString("password")
        );
    }
}
