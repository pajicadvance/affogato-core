package me.pajic.affogatocore.mixin.nightvision;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.FlowerBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FlowerBlock.class)
public class FlowerBlockMixin {

    @ModifyExpressionValue(
            method = "makeEffectList",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/core/Holder;I)Lnet/minecraft/world/item/component/SuspiciousStewEffects$Entry;"
            )
    )
    private static SuspiciousStewEffects.Entry swapNightVisionForInvisibility(
            SuspiciousStewEffects.Entry original,
            @Local(argsOnly = true, name = "effectSeconds") float effectSeconds
    ) {
        if (AffogatoCore.CONFIG.misc.nightVisionNuke.get() && original.effect() == MobEffects.NIGHT_VISION) {
            return new SuspiciousStewEffects.Entry(MobEffects.INVISIBILITY, Mth.floor(effectSeconds * 20.0F));
        }
        return original;
    }
}
