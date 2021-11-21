package de.cooperr.cooperrsurvival.message;

import de.cooperr.cooperrsurvival.CooperrSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final CooperrSurvival plugin;

    public PlayerJoinListener(CooperrSurvival plugin) {
        this.plugin = plugin;
        plugin.registerListener(this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.joinMessage(Component.text(player.getName() + " joined the server!", NamedTextColor.GREEN));
    }
}
