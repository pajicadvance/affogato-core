package me.pajic.affogatocore.mixin.combat;

import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyArg(
            method = "applyItemBlocking",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/component/BlocksAttacks;hurtBlockingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;F)V"
            ),
            index = 4
    )
    private float reduceProjectileShieldDamage(float original, @Local(argsOnly = true, name = "source") DamageSource source) {
        return source.is(DamageTypeTags.IS_PROJECTILE) ? original / AffogatoCore.CONFIG.combat.shieldProjectileDamageDivisor.get() : original;
    }
}
