package me.pajic.affogatocore.mixin.integration.penchant;

import archives.tater.penchant.component.EnchantmentProgress;
import archives.tater.penchant.registry.PenchantEnchantmentTags;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.affogatocore.integration.PenchantLevelUps;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@IfModLoaded("penchant")
@Mixin(EnchantmentProgress.class)
public class EnchantmentProgressMixin {

    @WrapMethod(method = "updateEnchantments")
    private static boolean holdLevelUps(
            EnchantmentProgress.Mutable progress,
            ItemEnchantments.Mutable enchantments,
            DataComponentGetter components,
            Operation<Boolean> original
    ) {
        if (!PenchantLevelUps.isEnabled()) return original.call(progress, enchantments, components);
        for (Holder<Enchantment> enchantment : enchantments.keySet()) {
            if (enchantment.is(PenchantEnchantmentTags.NO_LEVELING)) continue;
            int cap = PenchantLevelUps.getProgressCap(enchantment, enchantments.getLevel(enchantment), components);
            if (cap == 0) {
                progress.removeProgress(enchantment);
            } else if (progress.getProgress(enchantment) > cap) {
                progress.setProgress(enchantment, cap);
            }
        }
        return false;
    }

    @WrapMethod(method = "updateEnchantmentsForStack")
    private static void notifyLevelUpReady(
            EnchantmentProgress.Mutable progress,
            ItemEnchantments enchantments,
            ItemStack stack,
            @Nullable LivingEntity user,
            Operation<Void> original
    ) {
        if (!PenchantLevelUps.isEnabled() || !(user instanceof ServerPlayer player)) {
            original.call(progress, enchantments, stack, user);
            return;
        }
        EnchantmentProgress oldProgress = EnchantmentProgress.getProgress(stack);
        original.call(progress, enchantments, stack, user);
        for (Holder<Enchantment> enchantment : enchantments.keySet()) {
            int level = enchantments.getLevel(enchantment);
            int pendingBefore = PenchantLevelUps.getPendingLevels(enchantment, level, oldProgress.getProgress(enchantment), stack);
            int pendingAfter = PenchantLevelUps.getPendingLevels(enchantment, level, progress.getProgress(enchantment), stack);
            if (pendingAfter > pendingBefore) {
                PenchantLevelUps.notifyLevelUpReady(player, enchantment, level + pendingAfter);
            }
        }
    }
}
