package me.pajic.affogatocore.mixin.nightvision;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.storage.loot.functions.SetRandomPotionFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SetRandomPotionFunction.class)
public class SetRandomPotionFunctionMixin {

    @SuppressWarnings("unchecked")
    @ModifyExpressionValue(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Optional;get()Ljava/lang/Object;",
                    ordinal = 1
            )
    )
    private <T> T replaceNightVision(T original) {
        return AffogatoCore.CONFIG.misc.nightVisionNuke.get() && original == Potions.NIGHT_VISION ? (T) Potions.INVISIBILITY : original;
    }
}
