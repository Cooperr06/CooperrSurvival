package de.cooperr.cooperrsurvival.customenchantment;

import de.cooperr.cooperrsurvival.CooperrSurvival;
import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
public class Nausea extends Enchantment implements Listener {

    private final CooperrSurvival plugin;
    private final NamespacedKey namespacedKey;

    public Nausea(CooperrSurvival plugin) {
        super(new NamespacedKey(plugin, "nausea"));

        this.plugin = plugin;
        this.namespacedKey = new NamespacedKey(plugin, "nausea");

        Enchantment.registerEnchantment(this);
    }

    @Override
    public @NotNull String getName() {
        return "nausea";
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
        return EnchantmentTarget.BOW;
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
        return item.getType() == Material.BOW;
    }

    @Override
    public @NotNull Component displayName(int level) {
        return Component.text("Nausea " + (level == 1 ? "I" : level == 2 ? "II" : level == 3 ? "III" : ""), NamedTextColor.GOLD);
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
        return "effect.minecraft.nausea";
    }
}
