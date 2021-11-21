package de.cooperr.cooperrsurvival.message;

import de.cooperr.cooperrsurvival.CooperrSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final CooperrSurvival plugin;

    public PlayerQuitListener(CooperrSurvival plugin) {
        this.plugin = plugin;
        plugin.registerListener(this);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.quitMessage(Component.text(player.getName() + " left the server!", NamedTextColor.RED));
    }
}
