package de.cooperr.cooperrsurvival;

import de.cooperr.cooperrsurvival.customenchantment.Bleeding;
import de.cooperr.cooperrsurvival.customenchantment.Nausea;
import de.cooperr.cooperrsurvival.maintenance.MaintenanceCommand;
import de.cooperr.cooperrsurvival.message.PlayerJoinListener;
import de.cooperr.cooperrsurvival.message.PlayerQuitListener;
import de.cooperr.cooperrsurvival.tpa.TpaCommand;
import de.cooperr.cooperrsurvival.util.Config;
import de.cooperr.cooperrsurvival.util.MySqlConnection;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.logging.Level;

@Getter
public final class CooperrSurvival extends JavaPlugin {

    private Config settingsConfig;
    private Config playerConfig;
    private MySqlConnection mySql;

    @Override
    public void onEnable() {
        listenerRegistration();
        commandRegistration();
        enchantmentRegistration();

        settingsConfig = new Config(this, "settings.yml");
        playerConfig = new Config(this, "players.yml");
    }

    @Override
    public void onDisable() {
    }

    private void commandRegistration() {
        new TpaCommand(this);
        new MaintenanceCommand(this);
    }

    private void listenerRegistration() {
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
    }

    private void enchantmentRegistration() {
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to register enchantments!", e);
        }

        new Bleeding(this);
        new Nausea(this);
    }

    public void registerCommand(CommandExecutor executor, String name) {
        PluginCommand pluginCommand = getCommand(name);
        if (pluginCommand == null) {
            getLogger().log(Level.SEVERE, "Failed to register command \"" + name + "\"!");
            return;
        }
        pluginCommand.setExecutor(executor);
    }

    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public void sendUsageMessage(CommandSender sender, String usage) {
        sender.sendMessage(Component.text("Usage: " + usage, NamedTextColor.DARK_RED));
    }

    public void sendWrongSenderMessage(CommandSender sender) {
        sender.sendMessage(Component.text("You have to be a player to run this command!", NamedTextColor.DARK_RED));
    }
}
