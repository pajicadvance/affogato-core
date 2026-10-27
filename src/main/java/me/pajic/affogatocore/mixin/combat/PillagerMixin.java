package me.pajic.affogatocore.mixin.combat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.entity.monster.illager.Pillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Pillager.class)
public class PillagerMixin {

    @ModifyExpressionValue(
            method = "createAttributes",
            at = @At(
                    value = "CONSTANT",
                    args = "doubleValue=32.0"
            )
    )
    private static double modifyFollowRange(double original) {
        return AffogatoCore.CONFIG.combat.pillagerFollowRange.get();
    }
}
