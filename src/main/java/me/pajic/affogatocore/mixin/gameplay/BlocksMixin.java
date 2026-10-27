package me.pajic.affogatocore.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Blocks.class)
public class BlocksMixin {

    @ModifyExpressionValue(
            method = "lambda$static$71",
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=14"
            )
    )
    private static int setTorchLightLevel(int original) {
        return AffogatoCore.CONFIG.misc.torchLightLevel.get();
    }

    @ModifyExpressionValue(
            method = "lambda$static$73",
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=14"
            )
    )
    private static int setWallTorchLightLevel(int original) {
        return AffogatoCore.CONFIG.misc.torchLightLevel.get();
    }

    @ModifyExpressionValue(
            method = "lambda$static$152",
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=14"
            )
    )
    private static int setCopperTorchLightLevel(int original) {
        return AffogatoCore.CONFIG.misc.copperTorchLightLevel.get();
    }

    @ModifyExpressionValue(
            method = "lambda$static$154",
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=14"
            )
    )
    private static int setCopperWallTorchLightLevel(int original) {
        return AffogatoCore.CONFIG.misc.copperTorchLightLevel.get();
    }

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(
                    value = "CONSTANT",
                    args = "floatValue=0.98F"
            )
    )
    private static float setIceSlipperiness(float original) {
        return AffogatoCore.CONFIG.misc.iceFriction.get();
    }

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(
                    value = "CONSTANT",
                    args = "floatValue=0.989F"
            )
    )
    private static float setBlueIceSlipperiness(float original) {
        return AffogatoCore.CONFIG.misc.blueIceFriction.get();
    }
}
