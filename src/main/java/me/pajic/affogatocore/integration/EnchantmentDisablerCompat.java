package me.pajic.affogatocore.integration;

import me.pajic.enchantmentdisabler.ED;
import me.pajic.enchantmentdisabler.util.EnchantmentSources;
import net.minecraft.resources.Identifier;

public class EnchantmentDisablerCompat {

    public static boolean isDisabledInLoot(Identifier enchantment) {
        if (ED.CONFIG.disabler.disabledEnchantments.contains(enchantment)) return true;
        EnchantmentSources sources = ED.CONFIG.disabler.disabledEnchantmentsV2.get(enchantment);
        return sources != null && !sources.loot.get();
    }
}
