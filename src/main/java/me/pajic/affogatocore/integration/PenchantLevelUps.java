package me.pajic.affogatocore.integration;

import archives.tater.penchant.component.EnchantmentProgress;
import archives.tater.penchant.registry.PenchantComponents;
import archives.tater.penchant.registry.PenchantEnchantmentTags;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class PenchantLevelUps {

    public static boolean isEnabled() {
        return AffogatoCore.CONFIG.modIntegration.penchantPaidLevelUps.get();
    }

    public static int getPendingLevels(Holder<Enchantment> enchantment, int level, int progress, DataComponentGetter components) {
        if (level <= 0 || enchantment.is(PenchantEnchantmentTags.NO_LEVELING)) return 0;
        int maxLevel = enchantment.value().getMaxLevel();
        int pending = 0;
        while (level + pending < maxLevel) {
            int threshold = EnchantmentProgress.getMaxProgress(enchantment, level + pending, components);
            if (progress < threshold) break;
            progress -= threshold;
            pending++;
        }
        return pending;
    }

    public static int getPendingLevels(ItemStack stack, Holder<Enchantment> enchantment) {
        return getPendingLevels(
                enchantment,
                stack.getEnchantments().getLevel(enchantment),
                EnchantmentProgress.getProgress(stack).getProgress(enchantment),
                stack
        );
    }

    public static int getProgressCap(Holder<Enchantment> enchantment, int level, DataComponentGetter components) {
        long cap = 0;
        for (int i = level; i < enchantment.value().getMaxLevel(); i++) {
            cap += EnchantmentProgress.getMaxProgress(enchantment, i, components);
        }
        return (int) Math.min(cap, Integer.MAX_VALUE);
    }

    public static void levelUp(ItemStack stack, Holder<Enchantment> enchantment) {
        int level = stack.getEnchantments().getLevel(enchantment);
        int newLevel = level + 1;
        EnchantmentProgress.Mutable progress = EnchantmentProgress.getProgress(stack).toMutable();
        if (newLevel >= enchantment.value().getMaxLevel()) {
            progress.removeProgress(enchantment);
        } else {
            progress.addProgress(enchantment, -EnchantmentProgress.getMaxProgress(enchantment, level, stack));
        }
        EnchantmentHelper.updateEnchantments(stack, enchantments -> enchantments.set(enchantment, newLevel));
        stack.set(PenchantComponents.ENCHANTMENT_PROGRESS, progress.toImmutableOrNull());
    }

    public static void notifyLevelUpReady(ServerPlayer player, Holder<Enchantment> enchantment, int level) {
        player.sendOverlayMessage(Component.translatable(
                "message.affogatocore.penchant_level_up_ready",
                Enchantment.getFullname(enchantment, level)
        ));
        player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ENCHANTMENT_TABLE_USE,
                player.getSoundSource(),
                1,
                player.getRandom().nextFloat() * 0.1F + 0.9F
        );
    }
}
