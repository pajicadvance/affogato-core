package me.pajic.affogatocore.mixin.xp;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FishingHook.class)
public class FishingHookMixin {

    @WrapOperation(
            method = "retrieve",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",
                    ordinal = 1
            )
    )
    private boolean preventXpOrbSpawn(Level instance, Entity entity, Operation<Boolean> original) {
        return !AffogatoCore.CONFIG.experience.noFishingXp.get() && original.call(instance, entity);
    }
}
