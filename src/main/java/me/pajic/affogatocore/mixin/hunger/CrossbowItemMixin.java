package me.pajic.affogatocore.mixin.hunger;

import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @Inject(
            method = "tryLoadProjectiles",
            at = @At(
                    value = "RETURN",
                    ordinal = 0
            )
    )
    private static void crossbowExhaustion(LivingEntity shooter, ItemStack heldItem, CallbackInfoReturnable<Boolean> cir) {
        if (shooter instanceof Player player && !shooter.level().isClientSide()) {
            player.causeFoodExhaustion(AffogatoCore.CONFIG.exhaustionValues.drawRangedWeapon.get());
        }
    }
}
