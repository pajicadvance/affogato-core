package me.pajic.affogatocore.mixin.food;

import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.level.block.CakeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CakeBlock.class)
public class CakeBlockMixin {

    @ModifyArgs(
            method = "eat",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;eat(IF)V"
            )
    )
    private static void buffCake(Args args) {
        args.set(0, AffogatoCore.CONFIG.modIntegration.fdCakeSliceFoodLevel.get());
        args.set(1, AffogatoCore.CONFIG.modIntegration.fdCakeSliceSaturationLevel.get());
    }
}
