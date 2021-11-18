package de.cooperr.cooperrsurvival.tpa;

import de.cooperr.cooperrsurvival.CooperrSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TpaCommand implements TabExecutor {

    private final CooperrSurvival plugin;

    public TpaCommand(CooperrSurvival plugin) {
        this.plugin = plugin;
        plugin.registerCommand(this, "tpa");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            plugin.sendWrongSenderMessage(sender);
            return true;
        }

        Player player = (Player) sender;

        switch (args.length) {
            case 1 -> {
                Player target = plugin.getServer().getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(Component.text("This player does not exist or is offline!", NamedTextColor.DARK_RED));
                    return true;
                }

                target.sendMessage(player.displayName()
                        .append(Component.text(" sends you a teleport request!\n"))
                        .append(Component.text("[Accept]\t\t\t", NamedTextColor.DARK_GREEN, TextDecoration.BOLD)
                                .clickEvent(ClickEvent.runCommand("tpa accept " + target.getName())))
                        .append(Component.text("[Deny]", NamedTextColor.DARK_RED, TextDecoration.BOLD)
                                .clickEvent(ClickEvent.runCommand("tpa deny " + target.getName()))));
                return true;
            }

            case 2 -> {
                Player target = plugin.getServer().getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Component.text("This player does not exist or is offline!", NamedTextColor.DARK_RED));
                    return true;
                }

                if (args[0].equalsIgnoreCase("accept")) {

                    player.teleport(target);
                    player.sendMessage(Component.text("You were teleported to " + target.getName() + "!", NamedTextColor.DARK_GREEN));
                    target.sendMessage(Component.text(player.getName() + " was teleported to you!", NamedTextColor.DARK_GREEN));
                    return true;

                } else if (args[0].equalsIgnoreCase("deny")) {

                    player.sendMessage(Component.text(target.getName() + " denied your teleport request!", NamedTextColor.DARK_RED));
                    target.sendMessage(Component.text("The teleport request of " + player.getName() + " was denied!", NamedTextColor.DARK_RED));
                    return true;

                } else {
                    plugin.sendUsageMessage(player, "/tpa <Action> <Player>");
                    return true;
                }
            }

            default -> {
                plugin.sendUsageMessage(player, "/tpa <Player>");
                return true;
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        List<String> tabCompletion = new ArrayList<>();

        if (args.length == 0) {

            plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));
            tabCompletion.addAll(Arrays.asList("accept", "deny"));

            Collections.sort(tabCompletion);
            return tabCompletion;

        } else if (args.length == 1) {

            if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("deny")) {
                plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));

                Collections.sort(tabCompletion);
                return tabCompletion;
            }

            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                if (args[0].equals(onlinePlayer.getName())) {
                    return null;
                }
            }

            plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));
            tabCompletion.removeIf(s -> !s.startsWith(args[0]));

        } else if (args.length == 2) {

            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                if (args[1].equals(onlinePlayer.getName())) {
                    return null;
                }
            }

            plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));
            tabCompletion.removeIf(s -> !s.startsWith(args[0]));

            return tabCompletion;
        }
        return null;
    }
}
