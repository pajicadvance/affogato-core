package me.pajic.affogatocore.mixin.combat;

import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Enderman;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MeleeAttackGoal.class)
public class MeleeAttackGoalMixin {

    @Shadow @Final protected PathfinderMob mob;

    @ModifyArg(
            method = "resetAttackCooldown",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/MeleeAttackGoal;adjustedTickDelay(I)I"
            )
    )
    private int reduceAttackSpeed1(int original) {
        return reduceAttackSpeed(original);
    }

    @ModifyArg(
            method = "getAttackInterval",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/MeleeAttackGoal;adjustedTickDelay(I)I"
            )
    )
    private int reduceAttackSpeed2(int original) {
        return reduceAttackSpeed(original);
    }

    @Unique
    private int reduceAttackSpeed(int original) {
        return mob instanceof Enderman ? AffogatoCore.CONFIG.combat.endermanAttackDelay.get() : original;
    }
}
