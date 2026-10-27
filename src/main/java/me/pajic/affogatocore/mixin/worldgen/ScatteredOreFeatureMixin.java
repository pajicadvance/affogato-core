package me.pajic.affogatocore.mixin.worldgen;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.level.levelgen.feature.ScatteredOreFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ScatteredOreFeature.class)
public class ScatteredOreFeatureMixin {

    @ModifyExpressionValue(
            method = "place",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"
            )
    )
    private int setMinimumAncientDebrisAmount(int original) {
        return Math.max(original, AffogatoCore.CONFIG.worldgen.ancientDebrisMinAmount.get());
    }
}
