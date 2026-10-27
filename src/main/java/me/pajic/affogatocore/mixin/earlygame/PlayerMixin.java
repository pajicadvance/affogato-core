package me.pajic.affogatocore.mixin.earlygame;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {

    @ModifyExpressionValue(
            method = "hasCorrectToolForDrops",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;requiresCorrectToolForDrops()Z"
            )
    )
    private boolean requireAxeForLogs(boolean original, @Local(argsOnly = true, name = "state") BlockState state) {
        return AffogatoCore.CONFIG.packs.earlyGameExtension.get() && state.is(BlockTags.LOGS) || original;
    }
}
