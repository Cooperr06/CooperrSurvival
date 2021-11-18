package de.cooperr.cooperrsurvival;

import de.cooperr.cooperrsurvival.tpa.TpaCommand;
import de.cooperr.cooperrsurvival.util.Config;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@Getter
public final class CooperrSurvival extends JavaPlugin {

    private Config settingsConfig;
    private Config playerConfig;

    @Override
    public void onEnable() {
        listenerRegistration();
        commandRegistration();

        settingsConfig = new Config(this, "settings.yml");
        playerConfig = new Config(this, "players.yml");
    }

    @Override
    public void onDisable() {
    }

    private void commandRegistration() {
        new TpaCommand(this);
    }

    private void listenerRegistration() {

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
        sender.sendMessage("Usage: " + usage);
    }

    public void sendWrongSenderMessage(CommandSender sender) {
        sender.sendMessage(Component.text("You have to be a player to run this command!", NamedTextColor.DARK_RED));
    }
}
