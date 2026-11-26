package me.pajic.affogatocore.mixin.integration.penchant;

import archives.tater.penchant.menu.PenchantmentMenu;
import archives.tater.penchant.util.PenchantmentHelper;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.integration.PenchantLevelUps;
import me.pajic.affogatocore.integration.PenchantRequirements;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@IfModLoaded("penchant")
@Mixin(PenchantmentMenu.class)
public abstract class PenchantmentMenuMixin {

    @Shadow @Final private Container enchantSlots;
    @Shadow @Final private ContainerLevelAccess access;
    @Shadow @Final private Player player;

    @Shadow public abstract ItemStack getEnchantingStack();
    @Shadow public abstract boolean isEnchanting();

    @WrapMethod(method = "getBookCount(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)I")
    private static int countUniqueEnchantments(Level level, BlockPos pos, Operation<Integer> original) {
        if (AffogatoCore.CONFIG.modIntegration.penchantUniqueBookCount.get()) {
            return PenchantRequirements.countUniqueEnchantments(level, pos);
        }
        return original.call(level, pos);
    }

    @WrapMethod(method = "handleEnchant")
    private void levelUpEnchantment(Holder<Enchantment> enchantment, Operation<Void> original) {
        ItemStack stack = getEnchantingStack();
        if (!PenchantLevelUps.isEnabled() || !isEnchanting() || PenchantLevelUps.getPendingLevels(stack, enchantment) <= 0) {
            original.call(enchantment);
        } else {
            int levelCost = PenchantmentHelper.getXpLevelCost(enchantment);
            if (!player.hasInfiniteMaterials() && player.experienceLevel < levelCost) return;
            access.execute((level, pos) -> {
                player.onEnchantmentPerformed(stack, levelCost);
                PenchantLevelUps.levelUp(stack, enchantment);
                level.playSound(
                        null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS,
                        1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F
                );
                enchantSlots.setChanged();
            });
        }
    }
}
