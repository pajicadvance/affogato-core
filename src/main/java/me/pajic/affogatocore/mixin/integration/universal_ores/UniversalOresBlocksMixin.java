package me.pajic.affogatocore.mixin.integration.universal_ores;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import fr.hugman.universal_ores.block.UniversalOresBlocks;
import me.pajic.affogatocore.AffogatoCore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@IfModLoaded("universal_ores")
@Mixin(UniversalOresBlocks.class)
public class UniversalOresBlocksMixin {

    @ModifyArgs(
            method = "lambda$static$3",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setCoalOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.coal.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.coal.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$4",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setEmeraldOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.emerald.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.emerald.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$5",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setLapisOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.lapis.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.lapis.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$6",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setDiamondOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.diamond.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.diamond.get().component2());
    }

    @ModifyArgs(
            method = {"lambda$static$7", "lambda$static$9"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setNetherGoldOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.netherGold.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.netherGold.get().component2());
    }

    @ModifyArgs(
            method = {"lambda$static$8", "lambda$static$10"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setQuartzOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.quartz.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.quartz.get().component2());
    }
}
