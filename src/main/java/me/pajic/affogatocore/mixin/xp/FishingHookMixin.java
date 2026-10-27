package me.pajic.affogatocore.mixin.xp;

import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingHook.class)
public class FishingHookMixin {

    @Redirect(
            method = "retrieve",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",
                    ordinal = 1
            )
    )
    private boolean preventXpOrbSpawn(Level instance, Entity entity) {
        return !AffogatoCore.CONFIG.experience.noFishingXp.get();
    }
}
