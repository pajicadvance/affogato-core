package me.pajic.affogatocore.mixin.xp;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @WrapWithCondition(
            method = "dropAllDeathLoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;dropExperience(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;)V"
            )
    )
    private boolean noXpDrops(LivingEntity instance, ServerLevel level, Entity killer) {
        return !AffogatoCore.CONFIG.experience.noMobXpDrops.get() || instance.getType() == EntityTypes.PLAYER;
    }
}
