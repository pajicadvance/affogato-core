package me.pajic.affogatocore.mixin.hunger;

import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Block.class)
public class BlockMixin {

    @ModifyArg(
            method = "playerDestroy",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;causeFoodExhaustion(F)V"
            )
    )
    private float blockBreakExhaustion(float original) {
        return AffogatoCore.CONFIG.exhaustionValues.breakBlock.get();
    }
}
