package me.pajic.affogatocore.mixin.hunger;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    public ServerPlayerMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @ModifyArg(
            method = "jumpFromGround",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;causeFoodExhaustion(F)V",
                    ordinal = 0
            )
    )
    private float jumpSprintExhaustion(float original) {
        return AffogatoCore.CONFIG.exhaustionValues.jumpSprint.get();
    }

    @ModifyArg(
            method = "jumpFromGround",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;causeFoodExhaustion(F)V",
                    ordinal = 1
            )
    )
    private float jumpExhaustion(float original) {
        return AffogatoCore.CONFIG.exhaustionValues.jump.get();
    }

    @ModifyExpressionValue(
            method = "checkMovementStatistics",
            at = @At(
                    value = "CONSTANT",
                    args = "floatValue=0.1"
            )
    )
    private float sprintExhaustion(float original) {
        return AffogatoCore.CONFIG.exhaustionValues.sprint.get();
    }

    @ModifyExpressionValue(
            method = "checkMovementStatistics",
            at = @At(
                    value = "CONSTANT",
                    args = "floatValue=0.0",
                    ordinal = 0
            )
    )
    private float sneakExhaustion(float original) {
        return AffogatoCore.CONFIG.exhaustionValues.sneak.get();
    }

    @ModifyExpressionValue(
            method = "checkMovementStatistics",
            at = @At(
                    value = "CONSTANT",
                    args = "floatValue=0.01",
                    ordinal = 0
            )
    )
    private float swimExhaustion(float original) {
        return AffogatoCore.CONFIG.exhaustionValues.swimFast.get();
    }

    @ModifyExpressionValue(
            method = "checkMovementStatistics",
            at = @At(
                    value = "CONSTANT",
                    args = "floatValue=0.01",
                    ordinal = 2
            )
    )
    private float walkUnderwaterExhaustion(float original) {
        return AffogatoCore.CONFIG.exhaustionValues.swimSlow.get();
    }

    @ModifyExpressionValue(
            method = "checkMovementStatistics",
            at = @At(
                    value = "CONSTANT",
                    args = "floatValue=0.01",
                    ordinal = 4
            )
    )
    private float walkOnWaterExhaustion(float original) {
        return AffogatoCore.CONFIG.exhaustionValues.swimSlow.get();
    }

    @Inject(
            method = "checkMovementStatistics",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/stats/Stats;CLIMB_ONE_CM:Lnet/minecraft/resources/Identifier;",
                    opcode = Opcodes.GETSTATIC
            )
    )
    private void climbExhaustion(double dx, double dy, double dz, CallbackInfo ci) {
        causeFoodExhaustion(AffogatoCore.CONFIG.exhaustionValues.climb.get() * Math.round(dy * 100) * 0.01F);
    }

    @Inject(
            method = "checkMovementStatistics",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;isSwimming()Z"
            )
    )
    private void crawlExhaustion(double dx, double dy, double dz, CallbackInfo ci) {
        if (isVisuallyCrawling()) causeFoodExhaustion(AffogatoCore.CONFIG.exhaustionValues.crawl.get() * Math.round((float) Math.sqrt(dx * dx + dz * dz) * 100.0F) * 0.01F);
    }
}
