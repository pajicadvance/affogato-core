package me.pajic.affogatocore.mixin.misc;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.util.SoundUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.PotentSulfurBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotentSulfurBlockEntity.class)
public class PotentSulfurBlockEntityClientMixin {

    @ModifyExpressionValue(
            method = "lambda$static$3",
            at = @At(
                    value = "CONSTANT",
                    args = "longValue=40"
            )
    )
    private static long modifyGeyserSoundFrequency(long original) {
        return SoundUtil.scaleInterval((int) original, AffogatoCore.CONFIG.misc.potentSulfurGeyserSoundFrequencyMult.get());
    }

    @WrapWithCondition(
            method = "lambda$static$3",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"
            )
    )
    private static boolean limitGeyserSoundRange(Level level, double x, double y, double z, SoundEvent sound, SoundSource source, float volume, float pitch, boolean distanceDelay) {
        return SoundUtil.isInRange(x, y, z, AffogatoCore.CONFIG.misc.potentSulfurGeyserSoundRange.get());
    }
}
