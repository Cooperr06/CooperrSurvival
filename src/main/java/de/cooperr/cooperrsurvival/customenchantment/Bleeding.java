package de.cooperr.cooperrsurvival.customenchantment;

import de.cooperr.cooperrsurvival.CooperrSurvival;
import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
public class Bleeding extends Enchantment implements Listener {

    private final CooperrSurvival plugin;
    private final NamespacedKey namespacedKey;
    private final List<Entity> bleedingEntities = new ArrayList<>();
    private int taskId;

    public Bleeding(CooperrSurvival plugin) {
        super(new NamespacedKey(plugin, "bleeding"));

        this.plugin = plugin;
        this.namespacedKey = new NamespacedKey(plugin, "bleeding");

        Enchantment.registerEnchantment(this);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity entityDamager = event.getDamager();

        if (!(entity instanceof Damageable) || bleedingEntities.contains(entity) || !(entityDamager instanceof Player)) {
            return;
        }

        Player damager = (Player) entityDamager;

        if (!damager.getInventory().getItemInMainHand().containsEnchantment(this)) {
            return;
        }

        bleedingEntities.add(entity);
        int level = damager.getInventory().getItemInMainHand().getEnchantmentLevel(this);
        int[] current = {0};

        taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            if (current[0] == level * 2) {
                bleedingEntities.remove(entity);
                plugin.getServer().getScheduler().cancelTask(taskId);
            }

            ((Damageable) entity).damage(0.5, damager);
            current[0]++;
        }, 20, 20);
    }

    @Override
    public @NotNull String getName() {
        return "bleeding";
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return other.getKey().getKey().equals("fire_aspect");
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return item.getType().name().contains("SWORD") || item.getType().name().contains("AXE");
    }

    @Override
    public @NotNull Component displayName(int level) {
        return Component.text("Bleeding " + (level == 1 ? "I" : level == 2 ? "II" : level == 3 ? "III" : ""), NamedTextColor.GOLD);
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public boolean isDiscoverable() {
        return true;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.RARE;
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return Set.of(EquipmentSlot.HAND);
    }

    @Override
    public @NotNull String translationKey() {
        return "enchantment.cooperrsurvival.bleeding";
    }
}
