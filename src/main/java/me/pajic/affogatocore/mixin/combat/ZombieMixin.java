package me.pajic.affogatocore.mixin.combat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.entity.monster.zombie.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Zombie.class)
public abstract class ZombieMixin {

    @ModifyExpressionValue(
            method = "createAttributes",
            at = @At(
                    value = "CONSTANT",
                    args = "doubleValue=35.0"
            )
    )
    private static double modifyFollowRange(double original) {
        return AffogatoCore.CONFIG.combat.zombieFollowRange.get();
    }
}
