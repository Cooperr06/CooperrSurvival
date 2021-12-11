package de.cooperr.cooperrsurvival.message;

import de.cooperr.cooperrsurvival.CooperrSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    private final CooperrSurvival plugin;

    public PlayerLoginListener(CooperrSurvival plugin) {
        this.plugin = plugin;
        plugin.registerListener(this);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (player.isBanned()) {
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, Component.text("You are permanently banned from this server!", NamedTextColor.DARK_RED));
            return;
        } else if (!player.isWhitelisted() && plugin.getServer().hasWhitelist()) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Component.text("You are not whitelisted on this server!", NamedTextColor.DARK_RED));
            return;
        } else if (plugin.getServer().getMaxPlayers() == plugin.getServer().getOnlinePlayers().size()) {
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, Component.text("The server is currently full!", NamedTextColor.DARK_RED));
            return;
        } else if (plugin.getSettingsConfig().getBoolean("server.maintenance")) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("The server is currently in maintenance!", NamedTextColor.DARK_RED));
            return;
        } else if (player.getLastLogin() < 40) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Please wait a few seconds before reconnecting!", NamedTextColor.DARK_RED));
            return;
        }

        if (!plugin.getMySql().isPlayerRegistered(player)) {
            plugin.getMySql().registerNewPlayer(player);
        }
    }
}
