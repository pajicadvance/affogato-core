package me.pajic.affogatocore.mixin.hunger;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.effect.HungerMobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HungerMobEffect.class)
public class HungerMobEffectMixin {

    @ModifyExpressionValue(
            method = "applyEffectTick",
            at = @At(
                    value = "CONSTANT",
                    args = "floatValue=0.005"
            )
    )
    private float hungerEffectTickExhaustion(float original) {
        return AffogatoCore.CONFIG.exhaustionValues.hungerEffectTick.get();
    }
}
