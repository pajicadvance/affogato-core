package me.pajic.affogatocore.mixin.integration.appleskin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.affogatocore.AffogatoCore;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import squeek.appleskin.helpers.FoodHelper;

@IfModLoaded("appleskin")
@Mixin(value = FoodHelper.class, remap = false)
public class FoodHelperMixin {

    @ModifyExpressionValue(
            method = "getEstimatedHealthIncrement(IFF)F",
            at = @At(
                    value = "FIELD",
                    target = "Lsqueek/appleskin/helpers/FoodHelper;REGEN_EXHAUSTION_INCREMENT:F",
                    opcode = Opcodes.GETSTATIC
            )
    )
    private static float naturalHealingExhaustion(float original) {
        return AffogatoCore.CONFIG.exhaustionValues.naturalHealing.get();
    }

    @ModifyExpressionValue(
            method = "getEstimatedHealthIncrement(IFF)F",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Float;compare(FF)I"
            )
    )
    private static int noRapidHealing(int original) {
        return AffogatoCore.CONFIG.misc.noRapidHealing.get() ? -1 : original;
    }
}
