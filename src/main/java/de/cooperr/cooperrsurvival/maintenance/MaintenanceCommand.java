package de.cooperr.cooperrsurvival.maintenance;

import de.cooperr.cooperrsurvival.CooperrSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MaintenanceCommand implements TabExecutor {

    private final CooperrSurvival plugin;

    public MaintenanceCommand(CooperrSurvival plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length != 1) {
            plugin.sendUsageMessage(sender, "/maintenance <Action>");
            return true;
        }

        if (args[0].equalsIgnoreCase("on")) {

            if (plugin.getSettingsConfig().getBoolean("server.maintenance")) {
                sender.sendMessage(Component.text("Maintenance is already on!", NamedTextColor.DARK_RED));
                return true;
            }

            plugin.getSettingsConfig().set("server.maintenance", true);
            sender.sendMessage(Component.text("Maintenance is now set to on!", NamedTextColor.GOLD));

        } else if (args[0].equalsIgnoreCase("off")) {

            if (!plugin.getSettingsConfig().getBoolean("server.maintenance")) {
                sender.sendMessage(Component.text("Maintenance is already off!", NamedTextColor.DARK_RED));
                return true;
            }

            plugin.getSettingsConfig().set("server.maintenance", false);
            sender.sendMessage(Component.text("Maintenance is now set to off!", NamedTextColor.GOLD));

        } else {
            plugin.sendUsageMessage(sender, "/maintenance <Action>");
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        List<String> tabCompletion = new ArrayList<>();

        if (args.length == 0) {

            tabCompletion.addAll(Arrays.asList("on", "off"));

            Collections.sort(tabCompletion);
            return tabCompletion;

        } else if (args.length == 1) {

            if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off")) {
                return null;
            }

            tabCompletion.addAll(Arrays.asList("on", "off"));
            tabCompletion.removeIf(s -> !s.startsWith(args[0]));

            Collections.sort(tabCompletion);
            return tabCompletion;
        }
        return null;
    }
}
