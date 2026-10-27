package me.pajic.affogatocore.mixin.hunger;

import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @ModifyArg(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"
            )
    )
    private float attackExhaustion(float original) {
        return AffogatoCore.CONFIG.exhaustionValues.attack.get();
    }

    @ModifyArg(
            method = "actuallyHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"
            )
    )
    private float takeDamageExhaustion(float exhaustion) {
        return exhaustion == 0.1F ? AffogatoCore.CONFIG.exhaustionValues.takeDamage.get() : exhaustion;
    }
}
