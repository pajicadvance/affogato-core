package me.pajic.affogatocore.mixin.combat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Ravager.class)
public class RavagerMixin {

    @ModifyExpressionValue(
            method = "createAttributes",
            at = @At(
                    value = "CONSTANT",
                    args = "doubleValue=32.0"
            )
    )
    private static double modifyFollowRange(double original) {
        return AffogatoCore.CONFIG.combat.ravagerFollowRange.get();
    }

    @WrapOperation(
            method = "strongKnockback",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;push(DDD)V"
            )
    )
    private void modifyRoarKnockback(Entity entity, double xa, double ya, double za, Operation<Void> original) {
        float mult = AffogatoCore.CONFIG.combat.ravagerRoarKnockbackMult.get();
        original.call(entity, xa * mult, ya * mult, za * mult);
    }
}
