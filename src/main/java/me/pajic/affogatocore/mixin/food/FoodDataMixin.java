package me.pajic.affogatocore.mixin.food;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoodData.class)
public class FoodDataMixin {

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;isHurt()Z",
                    ordinal = 0
            )
    )
    private boolean noRapidHealing(boolean original) {
        return !AffogatoCore.CONFIG.misc.noRapidHealing.get() && original;
    }
}
