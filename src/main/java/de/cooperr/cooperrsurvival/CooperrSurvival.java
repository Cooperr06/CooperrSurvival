package de.cooperr.cooperrsurvival;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class CooperrSurvival extends JavaPlugin {

    @Override
    public void onEnable() {
        listenerRegistration();
        commandRegistration();
    }

    @Override
    public void onDisable() {
    }

    private void commandRegistration() {
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
}
